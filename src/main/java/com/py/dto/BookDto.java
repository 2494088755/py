package com.py.dto;

import com.py.entity.Book;
import lombok.Data;

import java.util.List;

@Data
public class BookDto {

    private List<Book> list;
    private Long total;
    private Integer pageSize;
    private Integer currentPage;
    private String keyword;
    private Long categoryId;

}
