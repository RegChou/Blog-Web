package com.muxui.blog.service.config.domain.vo;


import com.muxui.blog.common.base.PageResult;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author ou
 */
@Data
@Accessors(chain = true)
public class ConfigVO extends PageResult<ConfigVO> {
    private Integer type;


    private String configKey;


    private String configValue;

}
