package com.muxui.blog.service.auth.service.impl;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.auth.dao.AuthUserDao;
import com.muxui.blog.service.auth.dto.UserDTO;
import com.muxui.blog.service.auth.domain.AuthUser;
import com.muxui.blog.service.auth.service.AuthUserService;

import com.muxui.blog.system.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserDao, AuthUser> implements AuthUserService {

    @Autowired
    private AuthUserDao authUserDao;

    @Override
    public Result registerAdmin(UserDTO userDTO) {
        AuthUser authUser = new AuthUser();
        authUser.setName(userDTO.getEmail());
        authUser.setEmail(userDTO.getEmail());
        authUser.setRoleId(RoleEnum.ADMIN.getRoleId());
        authUser.setPassword(SecureUtil.md5(userDTO.getPassword()));
        authUserDao.insert(authUser);
        return Result.SUCCESS();
}
}
