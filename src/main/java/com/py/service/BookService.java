package com.py.service;

import com.py.dto.BookDto;
import com.py.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.py.utils.Result;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 24940
* @description 针对表【book】的数据库操作Service
* @createDate 2025-02-26 22:02:09
*/
public interface BookService extends IService<Book> {

    Result getBookList(BookDto params, HttpServletRequest request);

    Result getBookById(Long id, HttpServletRequest request);

    Result addBook(Book book, HttpServletRequest request);

    Result updateBook(Book book, HttpServletRequest request);

    Result deleteBook(Long id, HttpServletRequest request);
}
