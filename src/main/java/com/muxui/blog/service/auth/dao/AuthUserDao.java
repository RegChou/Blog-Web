package com.muxui.blog.service.auth.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muxui.blog.service.auth.domain.AuthUser;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 **/
@Repository
public interface AuthUserDao extends BaseMapper<AuthUser> {


}
