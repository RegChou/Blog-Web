package com.muxui.blog.service.posts.controller;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/22
 */

import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.util.ThrowableUtils;
import com.muxui.blog.service.posts.domain.vo.PostsVO;
import com.muxui.blog.service.posts.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/v1/list")
    public Result getFetchList(PostsVO postsVO) {
        return articleService.getFetchList(postsVO);
    }

    @PostMapping("/v1/add")
    public Result savePosts(@RequestBody PostsVO postsVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return articleService.savePosts(postsVO);
    }

}
