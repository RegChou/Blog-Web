package com.muxui.blog.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.muxui.blog.common.base.BeanTool;
import com.muxui.blog.common.base.Constants;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.service.auth.dao.AuthTokenDao;
import com.muxui.blog.service.auth.dao.AuthUserDao;
import com.muxui.blog.service.auth.domain.AuthToken;
import com.muxui.blog.service.auth.domain.AuthUser;
import com.muxui.blog.service.auth.domain.vo.UserSessionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;


public class SessionUtil {

    /**
     * 获取用户Session信息
     *
     * @return
     */
    public static UserSessionVO getUserSessionInfo() {

        // 获取请求对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 获取请求头Token值
        String token = Optional.ofNullable(request.getHeader(Constants.AUTHENTICATION)).orElse(null);

        if (StringUtils.isBlank(token)) {
            return null;
        }

        // 获取 token 中的 user id
        AuthUser authUser = null;
        try {
            authUser = JsonUtil.parseObject(JWT.decode(token).getAudience().get(0), AuthUser.class);
        } catch (JWTDecodeException j) {
            j.fillInStackTrace();
        }

        AuthUserDao userDao = BeanTool.getBean(AuthUserDao.class);
        AuthUser user = userDao.selectById(authUser.getId());
        if (user == null) {
            new Result(ResultCode.SERVER_ERROR);
        }

        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            new Result(ResultCode.INVALID_TOKEN);
        }

        AuthTokenDao authTokenDao = BeanTool.getBean(AuthTokenDao.class);
        Integer count = authTokenDao.selectCount(new LambdaQueryWrapper<AuthToken>().eq(AuthToken::getToken, token).eq(AuthToken::getUserId, user.getId()).ge(AuthToken::getExpireTime,
                LocalDateTime.now()));
        if (count.equals(com.baomidou.mybatisplus.core.toolkit.Constants.ZERO)) {
            new Result(ResultCode.INVALID_TOKEN);
        }

        UserSessionVO userSessionVO = new UserSessionVO();
        userSessionVO.setName(user.getName()).setRoleId(user.getRoleId()).setId(user.getId());
        return userSessionVO;
    }

}
