package com.muxui.blog.service.auth.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserSessionVO {

    private Long id;

    private String socialId;

    private String name;

    private String avatar;

    private Integer gender;

    private Long roleId;

    private String token;
}
