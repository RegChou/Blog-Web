package com.muxui.blog.service.config.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muxui.blog.service.config.dao.ConfigDao;
import com.muxui.blog.service.config.domain.Config;
import com.muxui.blog.service.config.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;

@Service
@Slf4j
public class ConfigServiceImpl extends ServiceImpl<ConfigDao, Config> implements ConfigService {


    @Autowired
    private ConfigDao configDao;

    @Resource
    private HandlerMapping resourceHandlerMapping;
}
