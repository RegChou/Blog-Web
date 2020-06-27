package com.muxui.blog.common.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muxui.blog.common.base.Constants;
import com.muxui.blog.common.base.ConstantsModels;
import com.muxui.blog.common.interceptor.AuthenticationInterceptor;
import com.muxui.blog.service.config.dao.ConfigDao;
import com.muxui.blog.service.config.domain.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ou
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    private ConfigDao configDao;

    @Bean
    @Primary
    public static ObjectMapper jacksonObjectMapper() {
        return JacksonConfig.jacksonObjectMapper();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {

        String defaultPath;
        try {
            Config config = configDao.selectOne(new LambdaQueryWrapper<Config>().eq(Config::getConfigKey, Constants.DEFAULT_PATH));
            defaultPath = config.getConfigValue();
        } catch (Exception e) {
            defaultPath = ConstantsModels.getDefaultPath(ConfigCache.getConfig(Constants.DEFAULT_PATH));
        }
        registry.addResourceHandler("/files/**").addResourceLocations("file:///" + defaultPath);
    }
}
