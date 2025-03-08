package com.py.service;

import com.py.entity.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.py.utils.Result;

/**
* @author 24940
* @description 针对表【order_item】的数据库操作Service
* @createDate 2025-02-27 23:50:47
*/
public interface OrderItemService extends IService<OrderItem> {

    Result getAllOrderItems();

    Result getOrderItemById(Long id);

    Result createOrderItem(OrderItem orderItem);

    Result updateOrderItem(Long id, OrderItem orderItem);

    Result deleteOrderItem(Long id);
}
