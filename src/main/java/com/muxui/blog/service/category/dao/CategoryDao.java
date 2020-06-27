package com.muxui.blog.service.category.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.muxui.blog.service.category.domain.Category;
import com.muxui.blog.service.category.domain.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDao extends BaseMapper<Category> {
    IPage<CategoryVO> selectStatistics(Page page, @Param(Constants.WRAPPER) Wrapper<CategoryVO> queryWrapper);

    List<CategoryVO> selectCategoryPostsTotal();

    IPage<Category> selectListPage(@Param("page") Page page, @Param("condition") CategoryVO categoryVO);
}
