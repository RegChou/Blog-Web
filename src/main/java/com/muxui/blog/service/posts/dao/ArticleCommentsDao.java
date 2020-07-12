package com.muxui.blog.service.posts.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.muxui.blog.service.posts.domain.ArticleComments;
import com.muxui.blog.service.posts.domain.vo.ArticleCommentsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCommentsDao extends BaseMapper<ArticleComments> {
    /**
     * 查询评论列表
     * @param page
     * @param articleId
     * @return
     */
    List<ArticleCommentsVo> selectArticleCommentsByArticleIdList(Page<ArticleCommentsVo> page, @Param("articleId") Long articleId);

    /**
     * 查询评论后台评论管理列表
     */
    List<ArticleCommentsVo> selectArticleCommentsList(Page<ArticleCommentsVo>  page, @Param("articleComments") ArticleCommentsVo articleCommentsVo);

    List<ArticleCommentsVo> selectArticleCommentsList(@Param("articleComments") ArticleCommentsVo articleComments);

}
