package com.muxui.blog.service.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muxui.blog.common.base.Constants;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.common.config.ConfigCache;
import com.muxui.blog.service.config.dao.ConfigDao;
import com.muxui.blog.service.config.domain.Config;
import com.muxui.blog.service.config.domain.vo.ConfigVO;
import com.muxui.blog.service.config.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ConfigServiceImpl extends ServiceImpl<ConfigDao, Config> implements ConfigService {


    @Autowired
    private ConfigDao configDao;

    @Resource
    private HandlerMapping resourceHandlerMapping;

    @Override
    public Result updateConfig(final List<ConfigVO> configList) {

        if (CollectionUtils.isEmpty(configList)) {
            return new Result(ResultCode.PARAM_INCORRECT);
        }

        final boolean b = configList.stream().anyMatch(configVO -> StringUtils.isBlank(configVO.getConfigKey()) || StringUtils.isBlank(configVO.getConfigValue()));
        if (b) {
            return new Result(ResultCode.PARAM_INCORRECT);
        }

        configList.forEach(configVO -> {
            if (configVO.getConfigKey() != null) {
                this.configDao.update(new Config().setConfigValue(configVO.getConfigValue()),
                        new LambdaQueryWrapper<Config>().eq(Config::getConfigKey, configVO.getConfigKey()));
                ConfigCache.putConfig(configVO.getConfigKey(), configVO.getConfigValue());
                if (configVO.getConfigKey().equals(Constants.DEFAULT_PATH)){
                    refreshCache(configVO.getConfigValue());
                }
            }
        });

        return Result.SUCCESS();
    }

    private void refreshCache(String path){
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = (SimpleUrlHandlerMapping) resourceHandlerMapping;
        Map<String, Object> handlerMap = simpleUrlHandlerMapping.getHandlerMap();
        ResourceHttpRequestHandler handler = (ResourceHttpRequestHandler) handlerMap.get("/files/**");
        handler.setLocationValues(Collections.singletonList("file:///"+path));
        List<org.springframework.core.io.Resource> locations = handler.getLocations();
        FileUrlResource fileUrlResource = null;
        try {
            fileUrlResource = new FileUrlResource(path);
            fileUrlResource.getFile();
        }catch (IOException e) {
            log.warn("修改路径失败");
        }
        locations.clear();
        locations.add(fileUrlResource);
    }

    @Override
    public Result getConfigList(final ConfigVO configVO) {
        final List<Config> configs;
        if (configVO.getType() == 1 || configVO.getType() == 4 || configVO.getType()== 5) {
            configs = this.configDao.selectList(new LambdaQueryWrapper<Config>().in(Config::getType, configVO.getType(), 3));
        } else {
            configs = this.configDao.selectList(new LambdaQueryWrapper<Config>().eq(Config::getType, configVO.getType()));
        }

        final List<ConfigVO> configVOList = new ArrayList<>();
        configs.forEach(config -> {
            final ConfigVO configVO1 = new ConfigVO();
            configVOList.add(configVO1.setConfigKey(config.getConfigKey()).setConfigValue(config.getConfigValue()));
        });

        return new Result(ResultCode.SUCCESS,configVOList);
    }

    @Override
    public Result getConfigBaseList() {
        return getConfigList(new ConfigVO().setType(0));
    }
}
