package com.py.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName order_item
 */
@TableName(value ="order_item")
@Data
public class OrderItem implements Serializable {
    /**
     * 订单项的唯一标识符
     */
    @TableId(type = IdType.AUTO)
    private Long orderItemId;

    /**
     * 关联的订单的唯一标识符
     */
    private Long orderId;

    /**
     * 关联的图书的唯一标识符
     */
    private Long bookId;

    /**
     * 图书的数量
     */
    private Integer quantity;

    /**
     * 图书的单价
     */
    private BigDecimal price;

    /**
     * 订单项创建的时间，该字段在插入时自动填充
     */
    @TableField(fill= FieldFill.INSERT)
    private Date createdAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}