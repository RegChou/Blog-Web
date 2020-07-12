package com.muxui.blog.service.category.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.category.domain.Tags;
import com.muxui.blog.service.category.domain.vo.TagsVO;

public interface TagsService extends IService<Tags> {

    /**
     * 查询全部标签
     * @param tagsVO
     * @return
     */
    Result getTagsList(TagsVO tagsVO);

    /**
     *  新增标签
     * @param tagsVO
     * @return
     */
    Result saveTags(TagsVO tagsVO);

    /**
     *  根据id查标签
     * @param id
     * @return
     */
    Result getTags(Long id);

    /**
     *  修改标签
     * @param tagsVO
     * @return
     */
    Result updateTags(TagsVO tagsVO);

    /**
     *  通过id删除标签
     * @param id
     * @return
     */
    Result deleteTags(Long id);

    /**
     * 查询标签的列表
     * @param tagsVO
     * @return
     */
    Result getTagsAndArticleQuantityList(TagsVO tagsVO);
}
