package com.muxui.blog.service.category.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.category.domain.Category;
import com.muxui.blog.service.category.domain.vo.CategoryVO;

/**
 * @author ou
 */
public interface CategoryService extends IService<Category> {

    /**
     * 查询分类列表
     * @param categoryVO
     * @return
     */
    Result getCategoryList(CategoryVO categoryVO);

    /**
     * 查询带有分类标签列表
     * @param categoryVO
     * @return
     */
    Result getCategoryTagsList(CategoryVO categoryVO);


    /**
     * 查询带有分类标签
     * @param id
     * @return
     */
    Result getCategoryTags(Long id);

    /**
     * 新增分类
     * @param categoryVO
     * @return
     */
    Result saveCategory(CategoryVO categoryVO);

    /**
     * 更新分类
     * @param categoryVO
     * @return
     */
    Result updateCategory(CategoryVO categoryVO);

    /**
     *  根据id删分类
     * @param id
     * @return
     */
    Result deleteCategory(Long id);
}
