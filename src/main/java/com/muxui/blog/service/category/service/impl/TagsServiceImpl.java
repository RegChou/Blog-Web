package com.muxui.blog.service.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.common.util.PageUtil;
import com.muxui.blog.service.category.dao.CategoryTagsDao;
import com.muxui.blog.service.category.dao.TagsDao;
import com.muxui.blog.service.category.domain.CategoryTags;
import com.muxui.blog.service.category.domain.Tags;
import com.muxui.blog.service.category.domain.vo.TagsVO;
import com.muxui.blog.service.category.service.TagsService;
import com.muxui.blog.service.posts.dao.InTagsDao;
import com.muxui.blog.service.posts.domain.InTags;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ou
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsDao, Tags> implements TagsService {

    @Autowired
    private TagsDao tagsDao;

    @Autowired
    private InTagsDao inTagsDao;

    @Autowired
    private CategoryTagsDao categoryTagsDao;

    @Override
    public Result getTagsList(TagsVO tagsVO) {
        List<TagsVO> tagsList = new ArrayList<>();
        if (tagsVO == null || tagsVO.getPage() == null || tagsVO.getSize() == null) {
            List<Tags> records = this.tagsDao.selectList(new LambdaQueryWrapper<Tags>().orderByDesc(Tags::getId));
            if (!CollectionUtils.isEmpty(records)) {
                records.forEach(tags -> {
                    tagsList.add(new TagsVO().setId(tags.getId()).setName(tags.getName()));
                });
            }
            return new Result(ResultCode.SUCCESS,tagsList);
        }
        LambdaQueryWrapper<Tags> tagsLambdaQueryWrapper = new LambdaQueryWrapper<Tags>();
        if (StringUtils.isNotBlank(tagsVO.getKeywords())){
            tagsLambdaQueryWrapper.like(Tags::getName, tagsVO.getKeywords());
        }
        if (StringUtils.isNotBlank(tagsVO.getName())){
            tagsLambdaQueryWrapper.eq(Tags::getName, tagsVO.getName());
        }
        Page page = PageUtil.checkAndInitPage(tagsVO);
        IPage<TagsVO> tagsIPage = this.tagsDao.selectPage(page,tagsLambdaQueryWrapper.orderByDesc(Tags::getId));
        return new Result(ResultCode.SUCCESS,tagsIPage.getRecords(), PageUtil.initPageInfo(page));
    }

    @Override
    public Result saveTags(TagsVO tagsVO) {
        this.tagsDao.insert(new Tags().setName(tagsVO.getName()));
        return Result.SUCCESS();
    }

    @Override
    public Result getTags(Long id) {
        Tags tags = this.tagsDao.selectById(id);
        return new Result(ResultCode.SUCCESS,new TagsVO().setId(tags.getId()).setName(tags.getName()));
    }

    @Override
    public Result updateTags(TagsVO tagsVO) {
        Integer count  = this.tagsDao.selectCount(new LambdaQueryWrapper<Tags>().eq(Tags::getId,tagsVO.getId()));
        if (count.equals(0)) {
            return new Result(ResultCode.DATA_NO_EXIST);
        }
        this.tagsDao.updateById(new Tags().setId(tagsVO.getId()).setName(tagsVO.getName()));
        return Result.SUCCESS();
    }

    @Override
    public Result deleteTags(Long id) {
        this.tagsDao.deleteById(id);
        this.categoryTagsDao.delete(new LambdaQueryWrapper<CategoryTags>().eq(CategoryTags::getTagsId, id));
        this.inTagsDao.delete(new LambdaQueryWrapper<InTags>().eq(InTags::getTagsId, id));
        return Result.SUCCESS();
    }

    @Override
    public Result getTagsAndArticleQuantityList(TagsVO tagsVO) {
        List<Tags> records = this.tagsDao.selectList(new LambdaQueryWrapper<Tags>().orderByDesc(Tags::getId));

        List<TagsVO> tagsList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(records)) {
            records.forEach(tags -> {
                Integer total = inTagsDao.selectCount(new LambdaQueryWrapper<InTags>().eq(InTags::getTagsId, tags.getId()));
                tagsList.add(new TagsVO().setId(tags.getId()).setArticleTotal(total).setName(tags.getName()));
            });
        }
        return new Result(ResultCode.SUCCESS,tagsList);
    }
}
