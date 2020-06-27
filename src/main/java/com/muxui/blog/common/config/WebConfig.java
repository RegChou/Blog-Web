package com.muxui.blog.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muxui.blog.common.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ou
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

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
}
