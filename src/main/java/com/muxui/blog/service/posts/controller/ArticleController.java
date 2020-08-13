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

    @OperateLog(module = "文章列表", code=OperateEnum.GET_POSTS_LIST)
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

    @OperateLog(module = "新增文章", code=OperateEnum.GET_POSTS_ADD)
    @PostMapping("/v1/add")
    public Result saveArticle(@RequestBody PostsVO postsVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return articleService.saveArticle(postsVO);
    }
    @OperateLog(module = "获取文章", code=OperateEnum.GET_POSTS_DETAIL)
    @GetMapping("/v1/{id}")
    public Result getArticle(@PathVariable Long id) {
        return this.articleService.getArticle(id);
    }


    @OperateLog(module = "修改文章", code=OperateEnum.GET_POSTS_EDIT)
    @PutMapping("/v1/update")
    @LoginRequired
    public Result updateArticle(@RequestBody PostsVO postsVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.articleService.updateArticle(postsVO);
    }

    @OperateLog(module = "删除文章", code=OperateEnum.GET_POSTS_DELE)
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
