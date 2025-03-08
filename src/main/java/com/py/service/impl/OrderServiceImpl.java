package com.py.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.py.dto.OrderDto;
import com.py.entity.Book;
import com.py.entity.Order;
import com.py.entity.User;
import com.py.service.BookService;
import com.py.service.OrderService;
import com.py.dao.OrderMapper;
import com.py.service.UserService;
import com.py.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public Result getOrdersByPage(OrderDto dto) {
        Page<Order> page = new Page<>(dto.getPage(), dto.getSize());
        Page<Order> result;
                LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
            .eq(dto.getUserId() != null, Order::getUserId, dto.getUserId())
            .eq(dto.getStatus() != null, Order::getStatus, dto.getStatus());
        result = page(page, queryWrapper);
        List<Order> records = result.getRecords();
        for (Order order : records) {
            Long bookId = order.getBookId();
            Book book = bookService.getById(bookId);
            User user = userService.getById(order.getUserId());
            order.setUsername(user.getUsername());
            String title = book.getTitle();
            order.setBookTitle(title);
        }
        return Result.success(Map.of("total", result.getTotal(), "data", result.getRecords()));
    }

    @Override
    public Result getOrderByUserId(Long id, int currentPage, int pageSize) {
        Page<Order> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, id);
        Page<Order> result = page(page, queryWrapper);
        return Result.success(Map.of("total", result.getTotal(), "data", result.getRecords()));
    }

    @Override
    public Result updateOrderStatus(Long id, Map<String, String> status) {
        Order order = getById(id);
        if (order == null) {
            return Result.error("404", "订单不存在");
        }

        // 如果状态更新为“已归还”，则增加图书库存
        if ("已归还".equals(status.get("status"))) {
            Book book = bookService.getById(order.getBookId());
            if (book != null) {
                book.setStock(book.getStock() + 1);
                bookService.updateById(book);
            }
        }

        order.setStatus(status.get("status"));
        return updateById(order) ? Result.success("订单状态更新成功") : Result.error("500", "订单状态更新失败");
    }

    @Override
    public Result deleteOrder(Long id) {
        Order order = getById(id);
        if (order == null) {
            return Result.error("404", "订单不存在");
        }
        return removeById(id) ? Result.success("订单删除成功") : Result.error("500", "订单删除失败");
    }

    @Override
    public Result createOrder(Order order) {
        return save(order) ? Result.success("订单创建成功") : Result.error("500", "订单创建失败");
    }

    @Override
    public Result checkBorrowStatus(HttpServletRequest request) {
        // 获取当前用户ID
        Long userId = getCurrentUserId(request);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userId)
                .and(w -> w.eq(Order::getStatus, "借阅中")
                        .or().eq(Order::getStatus, "审核中")
                        .or().eq(Order::getStatus, "超期未归还"));
        long count = count(queryWrapper);
        return Result.success(Map.of("hasActiveOrder", count > 0));
    }

    @Override
    public Result createBorrowOrder(Long bookId, Integer borrowTime, HttpServletRequest request) {
        // 获取当前用户ID
        Long userId = getCurrentUserId(request);
        Order order = new Order();
        order.setUserId(userId);
        order.setBorrowTime(borrowTime);
        order.setStatus("借阅中");
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        order.setReturnTime(new Date(System.currentTimeMillis() + borrowTime * 24 * 60 * 60 * 1000));
        order.setBookId(bookId);

        // 减少图书库存
        Book book = bookService.getById(bookId);
        if (book == null) {
            return Result.error("404", "图书不存在");
        }
        if (book.getStock() <= 0) {
            return Result.error("400", "库存不足");
        }
        book.setStock(book.getStock() - 1);
        bookService.updateById(book);

        return save(order) ? Result.success("借阅订单创建成功") : Result.error("500", "借阅订单创建失败");
    }

    @Override
    public Result handleReturn(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            return Result.error("404", "订单不存在");
        }
        if (!"借阅中".equals(order.getStatus()) && !"超期未归还".equals(order.getStatus())) {
            return Result.error("400", "订单状态不允许归还");
        }
        order.setStatus("审核中");
        order.setActualReturnTime(new Date());
        return updateById(order) ? Result.success("归还申请已提交，等待管理员审核") : Result.error("500", "归还申请提交失败");
    }

    @Override
    public Result confirmReturn(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            return Result.error("404", "订单不存在");
        }
        if (!"审核中".equals(order.getStatus())) {
            return Result.error("400", "订单状态不允许确认归还");
        }
        order.setStatus("已归还");
        order.setActualReturnTime(new Date());
        // 增加图书库存
        Book book = bookService.getById(order.getBookId());
        if (book != null) {
            book.setStock(book.getStock() + 1);
            bookService.updateById(book);
        }
        return updateById(order) ? Result.success("归还确认成功") : Result.error("500", "归还确认失败");
    }

    private Long getCurrentUserId(HttpServletRequest request) {
        // 从安全上下文中获取当前用户ID
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user != null) {
            User userObj = (User) user;
            return userObj.getUserId();
        }
        return 1L; // 示例代码，实际应从安全上下文中获取
    }
}