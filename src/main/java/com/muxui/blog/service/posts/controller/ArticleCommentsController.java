package com.muxui.blog.service.posts.controller;

import com.muxui.blog.common.annotation.LoginRequired;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.enums.RoleEnum;
import com.muxui.blog.service.posts.domain.vo.ArticleCommentsVo;
import com.muxui.blog.service.posts.service.ArticleCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comments")
public class ArticleCommentsController {

    @Autowired
    private ArticleCommentsService articleCommentsService;

    @LoginRequired(role = RoleEnum.USER)
    @PostMapping("/v1/add")
    public Result saveArticleComments(@RequestBody ArticleCommentsVo articleCommentsVo) {
        return this.articleCommentsService.saveArticleComments(articleCommentsVo);
    }

    @LoginRequired(role = RoleEnum.USER)
    @PostMapping("/admin/v1/reply")
    public Result replyComments(@RequestBody ArticleCommentsVo articleCommentsVo) {
        return this.articleCommentsService.replyComments(articleCommentsVo);
    }

    @LoginRequired
    @DeleteMapping("/v1/{id}")
    public Result deleteArticleComments(@PathVariable(value = "id") Long id) {
        return this.articleCommentsService.deleteArticleComments(id);
    }

    @LoginRequired
    @GetMapping("/v1/{id}")
    public Result getArticleComment(@PathVariable(value = "id") Long id) {
        return this.articleCommentsService.getArticleComment(id);
    }

    @GetMapping("/comments-Article/v1/list")
    public Result getArticleCommentsByArticleIdList(ArticleCommentsVo articleCommentsVo) {
        return this.articleCommentsService.getArticleCommentsByArticleIdList(articleCommentsVo);
    }

    @LoginRequired
    @GetMapping("/v1/get")
    public Result getArticleCommentsList(ArticleCommentsVo articleCommentsVo) {
        return this.articleCommentsService.getArticleCommentsList(articleCommentsVo);
    }
}
