package com.muxui.blog.service.category.controller;

import com.muxui.blog.common.annotation.LoginRequired;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.util.ThrowableUtils;
import com.muxui.blog.service.category.domain.vo.CategoryVO;
import com.muxui.blog.service.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ou
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @LoginRequired
    @GetMapping("/v1/list")
    public Result statisticsList(CategoryVO categoryVO){
        return categoryService.getCategoryList(categoryVO);
    }

    @LoginRequired
    @GetMapping("/category-tags/v1/list")
    public Result getCategoryTagsList(CategoryVO categoryVO) {
        return categoryService.getCategoryTagsList(categoryVO);
    }

    @LoginRequired
    @GetMapping("/category-tags/v1/{id}")
    public Result getCategoryTags(@PathVariable(value = "id", required = true) Long id) {
        return categoryService.getCategoryTags(id);
    }

    @LoginRequired
    @PutMapping("/v1/update")
    public Result updateCategory(@RequestBody CategoryVO categoryVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return categoryService.updateCategory(categoryVO);
    }

    @LoginRequired
    @PostMapping("/v1/add")
    public Result saveCategory(@RequestBody CategoryVO categoryVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return categoryService.saveCategory(categoryVO);
    }

    @LoginRequired
    @DeleteMapping("/v1/{id}")
    public Result deleteCategory(@PathVariable(value = "id", required = true) Long id) {
        return categoryService.deleteCategory(id);
    }
}
