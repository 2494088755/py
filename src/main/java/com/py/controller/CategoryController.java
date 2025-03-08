package com.py.controller;

import com.py.entity.Category;
import com.py.service.CategoryService;
import com.py.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping
    public Result createCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @GetMapping
    public Result getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Result getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public Result updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(category);
//        return Result.success(category);
    }

    @DeleteMapping("/{id}")
    public Result deleteCategory(@PathVariable Long id) {
//        return Result.success(null);
        return categoryService.deleteCategory(id);
    }

}
