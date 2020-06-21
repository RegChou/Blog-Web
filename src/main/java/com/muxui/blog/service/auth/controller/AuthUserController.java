package com.muxui.blog.service.auth.controller;

import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.enums.RoleEnum;
import com.muxui.blog.service.auth.domain.AuthUser;
import com.muxui.blog.service.auth.domain.vo.AuthUserVO;
import com.muxui.blog.service.auth.dto.EmailDTO;
import com.muxui.blog.service.auth.dto.UserDTO;
import com.muxui.blog.service.auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/16
 */
@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    private AuthUserService authUserService;

    @GetMapping("/user/v1/get")
    public Result getUserInfo(AuthUserVO authUserVO) {
        return authUserService.getUserInfo(authUserVO);
    }

    @PostMapping("/admin/v1/register")
    public Result registerAdminByGithub(@RequestBody UserDTO userDTO) {
        return authUserService.registerAdmin(userDTO);
    }

    @PostMapping("/admin/v1/email")
    public Result sendRegisterEmail(@RequestBody EmailDTO emailDTO) {
        return authUserService.sendRegisterEmail(emailDTO);
    }

    @PostMapping("/admin/v1/login")
    public Result adminLogin(@RequestBody AuthUserVO authUserVO) {
        return authUserService.login(authUserVO);
    }

    @PostMapping("/logout")
    public Result logout() {
        return authUserService.logout();
    }
}
