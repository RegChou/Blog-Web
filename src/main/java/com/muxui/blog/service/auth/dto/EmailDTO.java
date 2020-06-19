package com.muxui.blog.service.auth.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/19
 */
public class EmailDTO {

    @NotBlank(message = "邮箱不能为空")
    private String email;

    public String getEmail() {
        return email;
    }

    public EmailDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
