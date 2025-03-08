package com.py.service;

import com.py.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.py.utils.Result;

/**
* @author 24940
* @description 针对表【category】的数据库操作Service
* @createDate 2025-02-27 00:01:00
*/
public interface CategoryService extends IService<Category> {

    Result addCategory(com.py.entity.Category category);

    Result getAllCategories();

    Result getCategoryById(Long id);

    Result updateCategory(Category category);

    Result deleteCategory(Long id);
}
