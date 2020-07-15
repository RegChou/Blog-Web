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

    /**
     * 获取用户列表
     *
     * @param authUserVO
     * @return
     */
    Result getUserList(AuthUserVO authUserVO);

    Result deleteUsers(Long id);

    Result saveAuthUserStatus(AuthUserVO authUserVO);

    /**
     * 更新管理员个人资料
     *
     * @param authUserVO
     * @return
     */
    Result updateAdmin(AuthUserVO authUserVO);

    Result updatePassword(AuthUserVO authUserVO);

    /**
     * 获取作者信息
     *
     * @return
     */
    Result getMasterUserInfo();

    /**
     * 获取授权链接
     */
    Result oauthLoginByGithub();

    /**
     *  获取GitHub回调
     */
    String githubLogincallback(String code, String state);

    /**
     * 保存用户信息
     */
    Result saveUserByGithub(AuthUserVO authUserVO);
}
