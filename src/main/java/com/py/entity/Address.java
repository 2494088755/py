package com.py.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName address
 */
@TableName(value ="address")
@Data
public class Address implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private String receiverName;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private String address;

    /**
     * 
     */
    private Integer isDefault;
    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}