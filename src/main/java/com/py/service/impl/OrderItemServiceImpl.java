package com.py.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.py.entity.OrderItem;
import com.py.service.OrderItemService;
import com.py.dao.OrderItemMapper;
import com.py.utils.Result;
import org.springframework.stereotype.Service;

/**
* @author 24940
* @description 针对表【order_item】的数据库操作Service实现
* @createDate 2025-02-27 23:50:47
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService {

    @Override
    public Result getAllOrderItems() {
        return Result.success(list());
    }

    @Override
    public Result getOrderItemById(Long id) {
        return getById(id) != null ? Result.success(getById(id)) : Result.error("400","OrderItem not found");
    }

    @Override
    public Result createOrderItem(OrderItem orderItem) {
        return save(orderItem) ? Result.success(getById(orderItem.getOrderItemId())) : Result.error("400","OrderItem not created");
    }

    @Override
    public Result updateOrderItem(Long id, OrderItem orderItem) {
        return updateById(orderItem) ? Result.success(getById(id)) : Result.error("400","OrderItem not updated");
    }

    @Override
    public Result deleteOrderItem(Long id) {
        return removeById(id) ? Result.success("OrderItem deleted") : Result.error("400","OrderItem not deleted");
    }
}




