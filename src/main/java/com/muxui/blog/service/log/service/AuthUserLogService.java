package com.muxui.blog.service.log.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.log.domain.AuthUserLog;
import com.muxui.blog.service.log.domain.vo.AuthUserLogVO;

/**
 * 用户行为日志记录表:业务接口类
 */
public interface AuthUserLogService extends IService<AuthUserLog> {

    Result saveLogs(AuthUserLogVO authUserLogVO);

    /**
     * 分页查询用户行为日志记录表
     */
    Result getLogsList(AuthUserLogVO authUserLogVO);

}