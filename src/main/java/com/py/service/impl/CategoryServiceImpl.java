package com.py.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.py.entity.Category;
import com.py.service.CategoryService;
import com.py.dao.CategoryMapper;
import com.py.utils.Result;
import io.jsonwebtoken.lang.Objects;
import org.springframework.stereotype.Service;

/**
* @author 24940
* @description 针对表【category】的数据库操作Service实现
* @createDate 2025-02-27 00:01:00
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

    @Override
    public Result addCategory(Category category) {
        return save(category)? Result.success(category) : Result.error("400","Failed to add category");
    }

    @Override
    public Result getAllCategories() {
        return Result.success(list());
    }

    @Override
    public Result getCategoryById(Long id) {

        return Objects.isEmpty(getById(id))? Result.error("400","Category not found") : Result.success(getById(id));
    }

    @Override
    public Result updateCategory(Category category) {
        return updateById(category)? Result.success(category) : Result.error("400","Failed to update category");
    }

    @Override
    public Result deleteCategory(Long id) {
         return removeById(id)? Result.success(null) : Result.error("400","Failed to delete category");
    }
}