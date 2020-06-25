package com.muxui.blog.service.posts.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.posts.domain.Article;
import com.muxui.blog.service.posts.domain.vo.PostsVO;

public interface ArticleService extends IService<Article> {

    /**
     *  查询文章列表
     * @param postsVO
     * @return
     */
    Result getFetchList(PostsVO postsVO);

    /**
     *  保存文章
     * @param postsVO
     * @return
     */
    Result saveArticle(PostsVO postsVO);


    /**
     *  获取文章
     * @param id
     * @return
     */
    Result getArticle(Long id);
    /**
     *  修改文章内容
     * @param postsVO
     * @return
     */
    Result updateArticle(PostsVO postsVO);


    /**
     *  根据id删除文章
     * @param id
     * @return
     */
    Result deleteArticle(Long id);

    /**
     * 更新文章的状态
     * @param postsVO
     * @return
     */
    Result updateArticleStatus(PostsVO postsVO);
}
