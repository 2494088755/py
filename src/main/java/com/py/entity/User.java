package com.py.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2025-02-15 14:43:36
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long userId;

    private String username;

    private String password;

    @TableField(exist = false)
    private String checkPassword;

    @TableField(exist = false)
    private boolean keepSession;

    private String email;

    private String phone;

    private String address;

    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

}

