package com.py.dao;

import com.py.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 24940
* @description 针对表【category】的数据库操作Mapper
* @createDate 2025-02-27 00:01:00
* @Entity com.py.entity.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




