package com.muxui.blog.service.category.controller;

import com.muxui.blog.common.annotation.LoginRequired;
import com.muxui.blog.common.base.Result;
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


    @GetMapping("/v1/list")
    public Result getTagsList(TagsVO tagsVO) {
        return this.tagsService.getTagsList(tagsVO);
    }

    @GetMapping("/tags-article-quantity/v1/list")
    public Result getTagsAndArticleQuantityList(TagsVO tagsVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.tagsService.getTagsAndArticleQuantityList(tagsVO);
    }
    @LoginRequired
    @PostMapping("/v1/add")
    public Result saveTags(@RequestBody TagsVO tagsVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.tagsService.saveTags(tagsVO);
    }

    @GetMapping("/v1/{id}")
    public Result getTags(@PathVariable Long id) {
        return this.tagsService.getTags(id);
    }

    @LoginRequired
    @PutMapping("/v1/update")
    public Result updateTags(@RequestBody TagsVO tagsVO,BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.tagsService.updateTags(tagsVO);
    }

    @LoginRequired
    @DeleteMapping("/v1/{id}")
    public Result updateTags(@PathVariable Long id) {
        return this.tagsService.deleteTags(id);
    }
}
