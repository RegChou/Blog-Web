package com.muxui.blog.service.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.auth.dto.UserDTO;
import com.muxui.blog.service.auth.domain.AuthUser;

public interface AuthUserService extends IService<AuthUser> {

    /**
     *  注册管理员
     * @param userDTO
     * @return
     */
    Result registerAdmin(UserDTO userDTO);
}
