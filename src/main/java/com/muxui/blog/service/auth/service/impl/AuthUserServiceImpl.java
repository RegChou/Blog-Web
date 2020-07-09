package com.muxui.blog.service.auth.service.impl;


import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muxui.blog.common.base.BeanTool;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.common.enums.RoleEnum;
import com.muxui.blog.common.util.JwtUtil;
import com.muxui.blog.common.util.PageUtil;
import com.muxui.blog.common.util.SessionUtil;
import com.muxui.blog.service.auth.dao.AuthTokenDao;
import com.muxui.blog.service.auth.dao.AuthUserDao;
import com.muxui.blog.service.auth.domain.AuthToken;
import com.muxui.blog.service.auth.domain.vo.AuthUserVO;
import com.muxui.blog.service.auth.domain.vo.UserSessionVO;
import com.muxui.blog.service.auth.dto.EmailDTO;
import com.muxui.blog.service.auth.dto.UserDTO;
import com.muxui.blog.service.auth.domain.AuthUser;
import com.muxui.blog.service.auth.service.AuthUserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class AuthUserServiceImpl extends ServiceImpl<AuthUserDao, AuthUser> implements AuthUserService {

    @Autowired
    private AuthUserDao authUserDao;

    @Autowired
    private AuthTokenDao authTokenDao;

    //注入RedisTemplate
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result getUserInfo(AuthUserVO authUserVO) {
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        AuthUser authUser = authUserDao.selectById(userSessionInfo.getId());
        return new Result(ResultCode.SUCCESS,new AuthUserVO()
                .setStatus(authUser.getStatus())
                .setRoles(Collections.singletonList(RoleEnum.getEnumTypeMap().get(authUser.getRoleId()).getRoleName()))
                .setName(authUser.getName())
                .setIntroduction(authUser.getIntroduction())
                .setAvatar(authUser.getAvatar())
                .setEmail(authUser.getEmail()));
    }

    @Override
    public Result registerAdmin(UserDTO userDTO) {
        AuthUser authUser = authUserDao.selectOne(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getEmail, userDTO.getEmail()));
        if (authUser == null) {
            String code = redisTemplate.opsForValue().get("article_" + userDTO.getEmail());
            if (userDTO.getVerifyCode().equals(code)) {
                authUser = new AuthUser();
                authUser.setName(userDTO.getEmail());
                authUser.setEmail(userDTO.getEmail());
                authUser.setRoleId(RoleEnum.ADMIN.getRoleId());
                authUser.setPassword(SecureUtil.md5(userDTO.getPassword()));
                authUserDao.insert(authUser);
                return Result.SUCCESS();
            } else {
                return new Result(ResultCode.CAPTCHA_ERROR);
            }
        } else {
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
            redisTemplate.opsForValue().set("article_" + emailDTO.getEmail(), code, 5, TimeUnit.MINUTES);
            //此处填写邮件内容
            email.setMsg("您本次注册的验证码是:" + code);
            System.out.println("您本次注册的验证码是:" + code);
            email.send();
            return Result.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.FAIL();
        }
    }

    @Override
    public Result login(AuthUserVO authUserVO) {
        log.debug("login {}", authUserVO);
        if (StringUtils.isBlank(authUserVO.getEmail()) || StringUtils.isBlank(authUserVO.getPassword())) {
            return Result.ERROR();
        } else {
            AuthUser authUser = authUserDao.selectOne(new LambdaQueryWrapper<AuthUser>()
                    .eq(AuthUser::getRoleId, RoleEnum.ADMIN.getRoleId())
                    .eq(AuthUser::getEmail, authUserVO.getEmail()));
            if (authUser == null){
                return new Result(ResultCode.USERNOTEXIST);
            }
            String psw = SecureUtil.md5(authUserVO.getPassword());
            if (!authUser.getPassword().equals(psw)) {
                return new Result(ResultCode.MOBILEORPASSWORDERROR);
            }
            authUserVO.setRoles(Collections.singletonList(RoleEnum.getEnumTypeMap().get(authUser.getRoleId()).getRoleName()));
            String token = JwtUtil.createToken(new AuthUserVO().setPassword(authUser.getPassword()).setName(authUser.getName()).setId(authUser.getId()));
            authUserVO.setToken(token);
            LocalDateTime time = DateUtils.addHours(new Date(), 7 * 24).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
            authTokenDao.insert(new AuthToken().setUserId(authUser.getId()).setToken(token).setExpireTime(time));
            return new Result(ResultCode.LOGINSUCCESS, authUserVO);
        }
    }

    @Override
    public Result updateAdmin(AuthUserVO authUserVO) {
        if (authUserVO == null) {
            return new Result(ResultCode.PARAM_INCORRECT);
        }
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        authUserDao.updateById(new AuthUser()
                .setId(userSessionInfo.getId())
                .setEmail(authUserVO.getEmail())
                .setAvatar(authUserVO.getAvatar())
                .setName(authUserVO.getName())
                .setIntroduction(authUserVO.getIntroduction())
        );
        return Result.SUCCESS();
    }

    @Override
    public Result updatePassword(AuthUserVO authUserVO) {
        if (StringUtils.isBlank(authUserVO.getPassword()) || StringUtils.isBlank(authUserVO.getPasswordOld())) {
            return new Result(ResultCode.PARAM_INCORRECT);
        }
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        AuthUser authUser = authUserDao.selectById(userSessionInfo.getId());
        if (!SecureUtil.md5(authUserVO.getPasswordOld()).equals(authUser.getPassword())) {
            return new Result(ResultCode.UPDATE_PASSWORD_ERROR);
        }
        authUserDao.updateById(new AuthUser().setId(userSessionInfo.getId()).setPassword(SecureUtil.md5(authUserVO.getPassword())));
        return Result.SUCCESS();
    }

    @Override
    public Result logout() {
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        authTokenDao.delete(new LambdaQueryWrapper<AuthToken>().eq(AuthToken::getUserId, userSessionInfo.getId()));
        return Result.SUCCESS();
    }

    @Override
    public Result getUserList(AuthUserVO authUserVO) {
        Page page = Optional.ofNullable(PageUtil.checkAndInitPage(authUserVO)).orElse(PageUtil.initPage());
        LambdaQueryWrapper<AuthUser> authUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(authUserVO.getKeywords())) {
            authUserLambdaQueryWrapper.like(AuthUser::getName, authUserVO.getKeywords());
        }
        if (StringUtils.isNotBlank(authUserVO.getName())) {
            authUserLambdaQueryWrapper.eq(AuthUser::getName, authUserVO.getName());
        }
        if (authUserVO.getStatus() != null) {
            authUserLambdaQueryWrapper.eq(AuthUser::getStatus, authUserVO.getStatus());
        }

        IPage<AuthUser> authUserIPage = authUserDao.selectPage(page, authUserLambdaQueryWrapper.orderByDesc(AuthUser::getRoleId).orderByDesc(AuthUser::getId));
        List<AuthUser> records = authUserIPage.getRecords();

        List<AuthUserVO> authUserVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(records)) {
            records.forEach(authUser -> {
                authUserVOList.add(new AuthUserVO()
                        .setId(authUser.getId())
                        .setStatus(authUser.getStatus())
                        .setName(authUser.getName())
                        .setRoleId(authUser.getRoleId())
                        .setIntroduction(authUser.getIntroduction())
                        .setStatus(authUser.getStatus())
                );
            });
        }
        return new Result(ResultCode.SUCCESS,authUserVOList, PageUtil.initPageInfo(page));
    }

    @Override
    public Result deleteUsers(Long id) {
        if (id != null && authUserDao.selectCount(new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getId, id).eq(AuthUser::getRoleId, 2)) == 0) {
            authUserDao.deleteById(id);
            return Result.SUCCESS();
        }
        return Result.ERROR();
    }

    @Override
    public Result saveAuthUserStatus(AuthUserVO authUserVO) {
        if (authUserVO.getStatus() != null
                && authUserVO.getId() != null
                && authUserDao.selectCount(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getId, authUserVO.getId()).eq(AuthUser::getRoleId, 2)) == 0) {
            authUserDao.updateById(new AuthUser().setId(authUserVO.getId()).setStatus(authUserVO.getStatus()));
            return Result.SUCCESS();
        }
        return Result.ERROR();
    }
}
