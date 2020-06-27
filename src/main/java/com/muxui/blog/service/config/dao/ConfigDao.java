package com.muxui.blog.service.config.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muxui.blog.service.config.domain.Config;
import org.springframework.stereotype.Repository;


/**
 * @author ou
 */
@Repository
public interface ConfigDao extends BaseMapper<Config> {
}
