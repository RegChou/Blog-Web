package com.muxui.blog.service.category.controller;

import com.muxui.blog.common.annotation.LoginRequired;
import com.muxui.blog.common.annotation.OperateLog;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.enums.OperateEnum;
import com.muxui.blog.common.util.ThrowableUtils;
import com.muxui.blog.service.category.domain.vo.TagsVO;
import com.muxui.blog.service.category.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ou
 */
@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @OperateLog(module = "查询标签列表", code= OperateEnum.GET_TAGS_LIST)
    @GetMapping("/v1/list")
    public Result getTagsList(TagsVO tagsVO) {
        return this.tagsService.getTagsList(tagsVO);
    }

    @GetMapping("/tags-article-quantity/v1/list")
    public Result getTagsAndArticleQuantityList(TagsVO tagsVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.tagsService.getTagsAndArticleQuantityList(tagsVO);
    }
    @OperateLog(module = "新增标签", code= OperateEnum.GET_TAGS_ADD)
    @LoginRequired
    @PostMapping("/v1/add")
    public Result saveTags(@RequestBody TagsVO tagsVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.tagsService.saveTags(tagsVO);
    }

    @OperateLog(module = "查询标签详情", code= OperateEnum.GET_TAGS_DETAIL)
    @GetMapping("/v1/{id}")
    public Result getTags(@PathVariable Long id) {
        return this.tagsService.getTags(id);
    }

    @OperateLog(module = "编辑标签", code= OperateEnum.GET_TAGS_EDIT)
    @LoginRequired
    @PutMapping("/v1/update")
    public Result updateTags(@RequestBody TagsVO tagsVO,BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.tagsService.updateTags(tagsVO);
    }

    @OperateLog(module = "删除标签", code= OperateEnum.GET_TAGS_DELE)
    @LoginRequired
    @DeleteMapping("/v1/{id}")
    public Result deleteTags(@PathVariable Long id) {
        return this.tagsService.deleteTags(id);
    }
}
