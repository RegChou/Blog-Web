package com.muxui.blog.service.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muxui.blog.service.auth.domain.AuthToken;


import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenDao extends BaseMapper<AuthToken> {
}
