package com.muxui.blog.service.posts.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.muxui.blog.service.posts.domain.Article;
import com.muxui.blog.service.posts.domain.vo.PostsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends BaseMapper<Article> {
    /**
     * 查询文章列表
     * @param page
     * @param condition
     * @return
     */
    List<PostsVO> selectPostsList(Page<PostsVO> page, @Param("condition")PostsVO condition);

    /**
     * 根据id查询文章
     * @param id
     * @return
     */
    Article selectOneById(Long id);

    /**
     * 自增浏览量
     * @param id
     * @return
     */
    int incrementView(@Param("id") Long id);
}
