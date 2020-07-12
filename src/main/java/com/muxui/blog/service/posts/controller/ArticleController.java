package com.muxui.blog.service.posts.controller;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/22
 */

import com.muxui.blog.common.annotation.LoginRequired;
import com.muxui.blog.common.annotation.OperateLog;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.enums.OperateEnum;
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

    @GetMapping("/weight/v1/list")
    public Result getWeightList(PostsVO postsVO) {
        postsVO.setIsWeight(1);
        return articleService.getPostsList(postsVO);
    }

    @GetMapping("/archive/v1/list")
    public Result getArchiveTotalByDateList(PostsVO postsVO) {
        return articleService.getArchiveTotalByDateList(postsVO);
    }

    @PostMapping("/v1/add")
    public Result saveArticle(@RequestBody PostsVO postsVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return articleService.saveArticle(postsVO);
    }
    @GetMapping("/v1/{id}")
    public Result getArticle(@PathVariable Long id) {
        return this.articleService.getArticle(id);
    }

    @PutMapping("/v1/update")
    @LoginRequired
    public Result updateArticle(@RequestBody PostsVO postsVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.articleService.updateArticle(postsVO);
    }

    @LoginRequired
    @DeleteMapping("/v1/delete/{id}")
    public Result deleteArticle(@PathVariable Long id) {
        return this.articleService.deleteArticle(id);
    }

    @PutMapping("/status/v1/update")
    @LoginRequired
    public Result updateArticleStatus(@RequestBody PostsVO postsVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.articleService.updateArticleStatus(postsVO);
    }
}
