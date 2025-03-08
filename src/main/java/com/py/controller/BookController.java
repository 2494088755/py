package com.py.controller;

import com.py.dto.BookDto;
import com.py.entity.Book;
import com.py.service.BookService;
import com.py.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("list")
    public Result getBookList(@RequestBody BookDto params, HttpServletRequest request) {
        return bookService.getBookList(params, request);
    }

    @GetMapping("{id}")
    public Result getBookById(@PathVariable Long id, HttpServletRequest request) {
        return bookService.getBookById(id, request);
    }

    @PostMapping
    public Result addBook(@RequestBody Book book, HttpServletRequest request) {
        return bookService.addBook(book, request);
    }

    @PutMapping
    public Result updateBook(@RequestBody Book book, HttpServletRequest request) {
        return bookService.updateBook(book, request);
    }

    @DeleteMapping("{id}")
    public Result deleteBook(@PathVariable Long id, HttpServletRequest request) {
        return bookService.deleteBook(id, request);
    }
}
