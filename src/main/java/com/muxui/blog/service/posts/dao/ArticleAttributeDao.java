package com.muxui.blog.service.posts.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muxui.blog.service.posts.domain.ArticleAttribute;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleAttributeDao extends BaseMapper<ArticleAttribute> {
}
