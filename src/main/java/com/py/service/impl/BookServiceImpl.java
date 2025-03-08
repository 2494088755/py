package com.py.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.py.dto.BookDto;
import com.py.service.BookService;
import com.py.entity.Book;
import com.py.dao.BookDao;
import com.py.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl extends ServiceImpl<BookDao, Book>
    implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public Result getBookList(BookDto dto, HttpServletRequest request) {
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        String keyword = dto.getKeyword();
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like(Book::getTitle, keyword)
                        .or().like(Book::getAuthor, keyword)
                        .or().like(Book::getPublisher, keyword);
        }
        // 添加类别过滤条件
        Long categoryId = dto.getCategoryId();
        if (categoryId != null) {
            queryWrapper.eq(Book::getCategoryId, categoryId);
        }
        Page<Book> page = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        Page<Book> bookPage = bookDao.selectPage(page, queryWrapper);
        List<Book> books = bookPage.getRecords();
        BookDto bookDto = new BookDto();
        bookDto.setList(books);
        bookDto.setTotal(bookPage.getTotal());
        return Result.success(bookDto);
    }

    @Override
    public Result getBookById(Long id, HttpServletRequest request) {
        Book book = bookDao.selectById(id);
        if (book == null) {
            return Result.error("404", "书籍未找到");
        }
        return Result.success(book);
    }

    @Override
    public Result addBook(Book book, HttpServletRequest request) {
        // 校验书籍信息是否合法
        if (book.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return Result.error("400", "价格必须为正数");
        }
        if (book.getStock() < 0) {
            return Result.error("400", "库存不能为负数");
        }
        // 插入新书籍
        boolean isSaved = save(book);
        if (isSaved) {
            return Result.success("书籍添加成功");
        } else {
            return Result.error("500", "书籍添加失败");
        }
    }

    @Override
    public Result updateBook(Book book, HttpServletRequest request) {
        // 校验书籍是否存在
        Optional<Book> existingBook = Optional.ofNullable(bookDao.selectById(book.getId()));
        if (existingBook.isEmpty()) {
            return Result.error("404", "书籍不存在");
        }
        // 校验库存和价格
        if (book.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return Result.error("400", "价格必须为正数");
        }
        if (book.getStock() < 0) {
            return Result.error("400", "库存不能为负数");
        }
        // 更新书籍
        boolean isUpdated = updateById(book);
        if (isUpdated) {
            return Result.success("书籍更新成功");
        } else {
            return Result.error("500", "书籍更新失败");
        }
    }

    @Override
    public Result deleteBook(Long id, HttpServletRequest request) {
        // 校验书籍是否存在
        Book book = bookDao.selectById(id);
        if (book == null) {
            return Result.error("404", "书籍未找到");
        }
        // 删除书籍
        boolean isDeleted = removeById(id);
        if (isDeleted) {
            return Result.success("书籍删除成功");
        } else {
            return Result.error("500", "书籍删除失败");
        }
    }
}




