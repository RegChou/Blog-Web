package com.muxui.blog.service.posts.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.posts.domain.ArticleComments;
import com.muxui.blog.service.posts.domain.vo.ArticleCommentsVo;

public interface ArticleCommentsService  extends IService<ArticleComments> {
    /**
     * 新增评论
     */
    Result saveArticleComments(ArticleCommentsVo articleCommentsVo);

    /**
     * 根据文章的主键查询评论列表
     */
    Result getArticleCommentsByArticleIdList(ArticleCommentsVo articleCommentsVo);

    /**
     * 查询评论列表
     */
    Result getArticleCommentsList(ArticleCommentsVo articleCommentsVo);

    /**
     * 删除评论
     */
    Result deleteArticleComments(Long id);


    Result getArticleComment(Long id);

    Result replyComments(ArticleCommentsVo articleCommentsVo);
}
