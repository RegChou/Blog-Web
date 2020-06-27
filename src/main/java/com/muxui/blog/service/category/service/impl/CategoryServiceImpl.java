package com.muxui.blog.service.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.common.util.PageUtil;
import com.muxui.blog.service.category.dao.CategoryDao;
import com.muxui.blog.service.category.dao.CategoryTagsDao;
import com.muxui.blog.service.category.dao.TagsDao;
import com.muxui.blog.service.category.domain.Category;
import com.muxui.blog.service.category.domain.CategoryTags;
import com.muxui.blog.service.category.domain.Tags;
import com.muxui.blog.service.category.domain.vo.CategoryVO;
import com.muxui.blog.service.category.domain.vo.TagsVO;
import com.muxui.blog.service.category.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ou
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private TagsDao tagsDao;

    @Autowired
    private CategoryTagsDao categoryTagsDao;

    @Override
    public Result getCategoryList(CategoryVO categoryVO) {
        List<Category> categoryList = this.categoryDao.selectList(new LambdaQueryWrapper<Category>().orderByDesc(Category::getId));
        List<CategoryVO> categoryPostsTotal = this.categoryDao.selectCategoryPostsTotal();
        Map<Long, Integer> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(categoryPostsTotal)) {
            map = categoryPostsTotal.stream().collect(Collectors.toMap(CategoryVO::getId, CategoryVO::getTotal, Integer::sum));
        }

        List<CategoryVO> categoryVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(categoryList)) {
            Map<Long, Integer> finalMap = map;
            categoryList.forEach(category -> {
                Integer total = finalMap.get(category.getId());
                categoryVOList.add(new CategoryVO().setId(category.getId()).setName(category.getName()).setTotal(total));
            });
        }

        return new Result(ResultCode.SUCCESS,categoryVOList);
    }

    @Override
    public Result getCategoryTagsList(CategoryVO categoryVO) {
        Page page = Optional.ofNullable(PageUtil.checkAndInitPage(categoryVO)).orElse(PageUtil.initPage());
        IPage<Category> categoryIPage = this.categoryDao.selectListPage(page,categoryVO);
        List<Category> categoryList = categoryIPage.getRecords();

        List<CategoryVO> categoryVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(categoryList)) {
            categoryList.forEach(category -> {
                List<CategoryTags> categoryTags =
                        categoryTagsDao.selectList(new LambdaQueryWrapper<CategoryTags>().eq(CategoryTags::getCategoryId, category.getId()));
                List<TagsVO> tagsVOList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(categoryTags)) {
                    categoryTags.forEach(categoryTags1 -> {
                        Tags tags =
                                Optional.ofNullable(this.tagsDao.selectById(categoryTags1.getTagsId())).orElse(new Tags());
                        tagsVOList.add(new TagsVO().setName(tags.getName()));
                    });
                }
                categoryVOList.add(new CategoryVO().setId(category.getId()).setName(category.getName()).setTagsList(tagsVOList));
            });
        }

        return new Result(ResultCode.SUCCESS,categoryVOList, PageUtil.initPageInfo(page));
    }

    @Override
    public Result getCategoryTags(Long id) {
        Category category = this.categoryDao.selectOne(new LambdaQueryWrapper<Category>().eq(Category::getId, id));
        List<CategoryTags> categoryTags =
                categoryTagsDao.selectList(new LambdaQueryWrapper<CategoryTags>().eq(CategoryTags::getCategoryId,
                        category.getId()));
        List<TagsVO> tagsVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(categoryTags)) {
            categoryTags.forEach(categoryTags1 -> {
                Tags tags = this.tagsDao.selectById(categoryTags1.getTagsId());
                tagsVOList.add(new TagsVO().setId(tags.getId()).setName(tags.getName()));
            });
        }
        CategoryVO categoryVO = new CategoryVO().setId(category.getId()).setName(category.getName()).setTagsList(tagsVOList);
        return new Result(ResultCode.SUCCESS,categoryVO);
    }

    @Override
    public Result saveCategory(CategoryVO categoryVO) {
        Category category = new Category().setName(categoryVO.getName());
        this.categoryDao.insert(category);

        List<TagsVO> tagsList = categoryVO.getTagsList();
        if (!CollectionUtils.isEmpty(tagsList)) {
            tagsList.forEach(tagsVO -> {
                if (tagsVO.getId() == null) {
                    Tags tags = new Tags().setName(tagsVO.getName());
                    this.tagsDao.insert(tags);
                    tagsVO.setId(tags.getId());
                }
                categoryTagsDao.insert(new CategoryTags().setCategoryId(category.getId()).setTagsId(tagsVO.getId()));
            });
        }

        return Result.SUCCESS();
    }

    @Override
    public Result updateCategory(CategoryVO categoryVO) {
        Integer count = this.categoryDao.selectCount(new LambdaQueryWrapper<Category>().eq(Category::getId,
                categoryVO.getId()));
        if (count.equals(0)) {
            return new Result(ResultCode.DATA_NO_EXIST);
        }
        Category category = new Category().setId(categoryVO.getId()).setName(categoryVO.getName()).setUpdateTime(LocalDateTime.now());
        this.categoryDao.updateById(category);

        List<TagsVO> tagsList = categoryVO.getTagsList();

        this.categoryTagsDao.delete(new LambdaUpdateWrapper<CategoryTags>().eq(CategoryTags::getCategoryId, category.getId()));
        if (!CollectionUtils.isEmpty(tagsList)) {
            tagsList.forEach(tagsVO -> {
                if (tagsVO.getId() == null) {
                    // saveLogs
                    Tags tags =
                            new Tags().setName(tagsVO.getName()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now());
                    this.tagsDao.insert(tags);
                    tagsVO.setId(tags.getId());
                }
                categoryTagsDao.insert(new CategoryTags().setCategoryId(category.getId()).setTagsId(tagsVO.getId()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now()));
            });
        }
        return Result.SUCCESS();
    }

    @Override
    public Result deleteCategory(Long id) {
        this.categoryDao.deleteById(id);
        this.categoryTagsDao.delete(new LambdaQueryWrapper<CategoryTags>().eq(CategoryTags::getCategoryId, id));
        return Result.SUCCESS();
    }
}
