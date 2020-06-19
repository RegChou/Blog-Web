package com.muxui.blog.service.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.auth.dto.EmailDTO;
import com.muxui.blog.service.auth.dto.UserDTO;
import com.muxui.blog.service.auth.domain.AuthUser;

public interface AuthUserService extends IService<AuthUser> {

    /**
     *  注册管理员
     * @param userDTO
     * @return
     */
    Result registerAdmin(UserDTO userDTO);

    /**
     *  邮箱验证码
     * @param emailDTO
     * @return
     */
    Result sendRegisterEmail(EmailDTO emailDTO);

    /**
     * 登录
     * @param authUser
     * @return
     */
    Result login(AuthUser authUser);
}
