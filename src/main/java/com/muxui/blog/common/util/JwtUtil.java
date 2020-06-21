package com.muxui.blog.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.muxui.blog.service.auth.domain.vo.AuthUserVO;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/19
 */
public class JwtUtil {

    /**
     * 生成token
     *
     * @param authUserVO
     * @return
     */
    public static String createToken(AuthUserVO authUserVO) {
        String sign = authUserVO.getPassword();
        return JWT.create()
                // 发布者
                .withIssuer("muxui")
                // 生成签名的时间
                .withIssuedAt(new Date())
                // 生成签名的有效期
                .withExpiresAt(DateUtils.addHours(new Date(),7 * 24))
                .withAudience(JsonUtil.toJsonString(authUserVO.setPassword(null)))
                .sign(Algorithm.HMAC256(sign));
    }
}
