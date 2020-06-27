package com.muxui.blog.service.config.controller;


import com.muxui.blog.common.annotation.LoginRequired;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.config.domain.vo.ConfigVO;
import com.muxui.blog.service.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @LoginRequired
    @PutMapping("/v1/update")
    public Result updateConfig(@RequestBody List<ConfigVO> configList) {
        return configService.updateConfig(configList);
    }

    @LoginRequired
    @GetMapping("/v1/list")
    public Result getConfigList(ConfigVO configVO) {
        return configService.getConfigList(configVO);
    }

    @GetMapping("/config-base/v1/list")
    public Result getConfigBaseList() {
        return configService.getConfigBaseList();
    }
}
