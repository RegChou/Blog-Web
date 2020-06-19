package com.muxui.blog;

import cn.hutool.crypto.SecureUtil;
import com.muxui.blog.service.auth.dao.AuthUserDao;
import com.muxui.blog.service.auth.domain.AuthUser;
import com.muxui.blog.system.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private AuthUserDao authUserDao;

    @Test
    void contextLoads() {

    }

}
