package com.muxui.blog.service.auth.dto;
import cn.hutool.crypto.SecureUtil;

import javax.validation.constraints.NotBlank;
/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/16
 */
public class UserDTO {

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "手机号码不能为空")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    public String getPassword() {
        return password;
    }

    public UserDTO setPassword(String password) {
        this.password = SecureUtil.sha256(password);
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public UserDTO setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
        return this;
    }
}
