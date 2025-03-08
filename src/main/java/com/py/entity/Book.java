package com.py.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @TableName book
 */
@TableName(value = "book")
@Data
public class Book implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 书籍标题
     */
    private String title;

    /**
     * 作者姓名
     */
    private String author;

    /**
     * 出版社名称
     */
    private String publisher;

    /**
     * 国际标准书号 (ISBN)，必须符合 ISBN 格式
     */
    private String isbn;

    /**
     * 书籍价格，必须为正数
     */
    private BigDecimal price;

    /**
     * 库存数量，不能为负数
     */
    private Integer stock;

    /**
     * 书籍描述
     */
    private String description;

    /**
     * 封面图片 URL
     */
    private String coverImage;

    /**
     * 分类 ID
     */
    private Long categoryId;

    /**
     * 创建时间，默认为当前时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt = new Date();

    /**
     * 更新时间，默认为当前时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt = new Date();


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}