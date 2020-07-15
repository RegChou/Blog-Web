package com.muxui.blog.service.auth.controller;

import com.muxui.blog.common.annotation.LoginRequired;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.enums.RoleEnum;
import com.muxui.blog.service.auth.domain.AuthUser;
import com.muxui.blog.service.auth.domain.vo.AuthUserVO;
import com.muxui.blog.service.auth.dto.AccessTokenDTO;
import com.muxui.blog.service.auth.dto.EmailDTO;
import com.muxui.blog.service.auth.dto.UserDTO;
import com.muxui.blog.service.auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

    @LoginRequired
    @PutMapping("/admin/v1/update")
    public Result updateAdmin(@RequestBody AuthUserVO authUserVO) {
        return authUserService.updateAdmin(authUserVO);
    }

    @LoginRequired
    @PutMapping("/password/v1/update")
    public Result updatePassword(@RequestBody AuthUserVO authUserVO) {
        return authUserService.updatePassword(authUserVO);
    }

    @GetMapping("/master/v1/get")
    public Result getMasterUserInfo() {
        return authUserService.getMasterUserInfo();
    }

    @PostMapping("/v1/logout")
    public Result logout() {
        return authUserService.logout();
    }

    @LoginRequired
    @GetMapping("/user/v1/list")
    public Result getUserList(AuthUserVO authUserVO) {
        return authUserService.getUserList(authUserVO);
    }

    @PostMapping("/user/v1/login")
    public Result saveUserByGithub(@RequestBody AuthUserVO authUserVO) {
        return authUserService.saveUserByGithub(authUserVO);
    }
    @DeleteMapping("/user/v1/{id}")
    public Result deleteUser(@PathVariable Long id) {
        return authUserService.deleteUsers(id);
    }

    @LoginRequired(role = RoleEnum.ADMIN)
    @PutMapping("/status/v1/update")
    public Result saveAuthUserStatus(@RequestBody AuthUserVO authUserVO) {
        return authUserService.saveAuthUserStatus(authUserVO);
    }

    @GetMapping("/github/v1/get")
    public Result oauthLoginByGithub(){
        return authUserService.oauthLoginByGithub();
    }

    @GetMapping("/github/callback")
    public String callback(@RequestParam String code, @RequestParam String state) {
        return authUserService.githubLogincallback(code,state);
    }
}
