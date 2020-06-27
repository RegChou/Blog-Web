package com.muxui.blog.service.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.config.domain.Config;
import com.muxui.blog.service.config.domain.vo.ConfigVO;

import java.util.List;

public interface ConfigService extends IService<Config> {
    /**
     * 更新配置
     * @param configList
     * @return
     */
    Result updateConfig(List<ConfigVO> configList);

    /**
     * 查询配置
     * @param configVO
     * @return
     */
    Result getConfigList(ConfigVO configVO);

    /**
     * 查询基础配置
     * @return
     */
    Result getConfigBaseList();
}
