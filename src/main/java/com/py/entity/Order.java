package com.py.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName order
 */
@TableName(value ="tb_order")
@Data
public class Order implements Serializable {
    /**
     * 订单ID，主键，自增。
     */
    @TableId(type = IdType.AUTO)
    private Long orderId;

    /**
     * 用户ID，表示该订单所属的用户。
     */
    private Long userId;

    /**
     * 图书ID，表示该订单借阅的图书。
     */
    private Long bookId;

    /**
     * 借阅时间，单位为天。
     */
    private Integer borrowTime;

    /**
     * 归还时间
     */
    private Date returnTime;

    @TableField(exist = false)
    private String username;

    /**
     * 订单状态，例如："借阅中"、"已归还"、"超期未归还"、"审核中"。
     */
    private String status;

    /**
     * 实际归还时间。
     */
    private Date actualReturnTime;

    @TableField(exist = false)
    private String bookTitle;

    /**
     * 订单创建时间，自动填充，仅在插入时填充。
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    /**
     * 订单更新时间，自动填充，在插入和更新时填充。
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}