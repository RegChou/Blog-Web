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
    Result savePosts(PostsVO postsVO);
}
