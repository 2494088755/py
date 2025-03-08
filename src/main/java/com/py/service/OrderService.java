package com.py.service;

import com.py.dto.OrderDto;
import com.py.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.py.utils.Result;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface OrderService extends IService<Order> {
    Result getOrdersByPage(OrderDto dto);

    Result getOrderByUserId(Long id, int currentPage, int pageSize);

    Result updateOrderStatus(Long id, Map<String, String> status);

    Result deleteOrder(Long id);

    Result createOrder(Order order);

    Result checkBorrowStatus(HttpServletRequest request);

    Result createBorrowOrder(Long bookId, Integer borrowTime,HttpServletRequest request);

    Result handleReturn(Long orderId);

    Result confirmReturn(Long orderId);
}