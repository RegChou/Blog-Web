package com.muxui.blog.service.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.auth.domain.vo.AuthUserVO;
import com.muxui.blog.service.auth.dto.EmailDTO;
import com.muxui.blog.service.auth.dto.UserDTO;
import com.muxui.blog.service.auth.domain.AuthUser;

public interface AuthUserService extends IService<AuthUser> {

    Result getUserInfo(AuthUserVO authUserVO);

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
     * @param authUserVO
     * @return
     */
    Result login(AuthUserVO authUserVO);

    /**
     * 退出登录
     * @return
     */
    Result logout();
}
