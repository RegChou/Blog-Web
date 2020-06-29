package com.muxui.blog.common.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.muxui.blog.common.base.BeanTool;
import com.muxui.blog.common.enums.RoleEnum;
import com.muxui.blog.service.auth.dao.AuthUserDao;
import com.muxui.blog.service.auth.domain.AuthUser;
import com.muxui.blog.service.config.dao.ConfigDao;
import com.muxui.blog.service.config.domain.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Slf4j
@Component
@DependsOn({"dataSource"})
public class InitSystemConfig implements ApplicationListener<ApplicationContextEvent>, Ordered {

    @Autowired
    private ConfigDao configDao;


    public void init() {

        List<Config> configList = configDao.selectList(null);
        configList.forEach(config -> {
            log.debug("config_key: {}, config_vlaue: {}", config.getConfigKey(), config.getConfigValue());
            ConfigCache.putConfig(config.getConfigKey(), config.getConfigValue());
        });
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent applicationContextEvent) {
        init();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}