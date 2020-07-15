package com.muxui.blog.service.auth.domain.vo;

import com.muxui.blog.common.base.PageResult;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class AuthUserVO extends PageResult<AuthUserVO> {

    /**
     * 主键
     */
    private Long id;

    /**
     * 社交账户ID
     */
    private String socialId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    private String passwordOld;
    /**
     * 角色主键 1 普通用户 2 admin
     */
    private Long roleId;

    /**
     * 头像
     */
    private String avatar;


    private String token;

    private List<String> roles;

    private String introduction;

    private Integer status;

    /**
     * 邮箱
     */
    private String email;

    private Integer userCount;
    private Integer toDayNew;

    private String htmlUrl;
}
