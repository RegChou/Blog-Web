package com.muxui.blog.service.auth.service.impl;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.common.enums.RoleEnum;
import com.muxui.blog.common.util.JwtUtil;
import com.muxui.blog.service.auth.dao.AuthTokenDao;
import com.muxui.blog.service.auth.dao.AuthUserDao;
import com.muxui.blog.service.auth.domain.AuthToken;
import com.muxui.blog.service.auth.dto.EmailDTO;
import com.muxui.blog.service.auth.dto.UserDTO;
import com.muxui.blog.service.auth.domain.AuthUser;
import com.muxui.blog.service.auth.service.AuthUserService;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserDao, AuthUser> implements AuthUserService {

    @Autowired
    private AuthUserDao authUserDao;

    @Autowired
    private AuthTokenDao authTokenDao;

    //注入RedisTemplate
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result registerAdmin(UserDTO userDTO) {
        AuthUser authUser = authUserDao.selectOne(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getEmail, userDTO.getEmail()));
        if (authUser == null){
            String code = redisTemplate.opsForValue().get("article_" + userDTO.getEmail());
            if (userDTO.getVerifyCode().equals(code)){
                authUser = new AuthUser();
                authUser.setName(userDTO.getEmail());
                authUser.setEmail(userDTO.getEmail());
                authUser.setRoleId(RoleEnum.ADMIN.getRoleId());
                authUser.setPassword(SecureUtil.md5(userDTO.getPassword()));
                authUserDao.insert(authUser);
                return Result.SUCCESS();
            }else {
                return new Result(ResultCode.CAPTCHA_ERROR);
            }
        }else {
            return new Result(ResultCode.ACCOUNT_EXIST);
        }
}

    @Override
    public Result sendRegisterEmail(EmailDTO emailDTO) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.163.com");
            email.setCharset("UTF-8");
            // 收件地址
            email.addTo(emailDTO.getEmail());
            //此处填邮箱地址和用户名,用户名可以任意填写
            email.setFrom("ouyangzoheng@163.com", "沐旭i");
            //此处填写邮箱地址和客户端授权码
            email.setAuthentication("ouyangzoheng@163.com", "RJMWZPBVUSYTQKRE");
            //此处填写邮件名，邮件名可任意填写
            email.setSubject("沐旭Blog");
            // 6位随机验证码
            String code = RandomStringUtils.randomNumeric(6);
            // 把验证码存到redis
            redisTemplate.opsForValue().set("article_" + emailDTO.getEmail(),code,5, TimeUnit.MINUTES);
            //此处填写邮件内容
            email.setMsg("尊敬的用户您好,您本次注册的验证码是:" + code);
            System.out.println("您本次注册的验证码是:" + code);
            email.send();
            return Result.SUCCESS();
        }
        catch(Exception e){
            e.printStackTrace();
            return Result.FAIL();
        }
    }

    @Override
    public Result login(AuthUser authUser) {
        if (StringUtils.isBlank(authUser.getEmail()) || StringUtils.isBlank(authUser.getPassword())){
            return Result.ERROR();
        }else {
            AuthUser user = authUserDao.selectOne(new LambdaQueryWrapper<AuthUser>()
                    .eq(AuthUser::getRoleId, RoleEnum.ADMIN.getRoleId())
                    .eq(AuthUser::getEmail, authUser.getEmail()));
            String psw = SecureUtil.md5(authUser.getPassword());
            if (!user.getPassword().equals(psw)){
                return new Result(ResultCode.MOBILEORPASSWORDERROR);
            }
            String token = JwtUtil.createToken(psw);
            authTokenDao.insert(new AuthToken().setUserId(user.getId()).setToken(token).setExpireTime(DateUtils.addHours(new Date(),7 * 24).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime()));
            return new Result(ResultCode.LOGINSUCCESS,user);
        }
    }
}
