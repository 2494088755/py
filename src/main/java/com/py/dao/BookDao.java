package com.py.dao;

import com.py.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 24940
* @description 针对表【book】的数据库操作Mapper
* @createDate 2025-02-26 22:02:09
* @Entity com.py.entity.Book
*/
@Mapper
public interface BookDao extends BaseMapper<Book> {

}




