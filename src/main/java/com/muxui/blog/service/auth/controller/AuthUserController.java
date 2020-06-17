package com.muxui.blog.service.auth.controller;

import com.muxui.blog.common.base.Result;
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


    @PostMapping("/admin/v1/register")
    public Result registerAdminByGithub(@RequestBody UserDTO userDTO) {
        return authUserService.registerAdmin(userDTO);
    }

}
