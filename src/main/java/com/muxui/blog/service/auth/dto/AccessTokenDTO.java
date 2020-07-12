package com.muxui.blog.service.auth.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/7/11
 */
@Data
@Accessors(chain = true)
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
