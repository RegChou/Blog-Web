package com.muxui.blog.service.log.controller;



import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.log.domain.vo.AuthUserLogVO;
import com.muxui.blog.service.log.service.AuthUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户行为日志记录表: 后端controller类
 */
@RestController
@RequestMapping("/logs")
public class AuthUserLogController {
    
    
    @Autowired
    private AuthUserLogService authUserLogServiceImpl;
    
    /**
     * 分页查询用户行为日志记录表
     */
    @GetMapping("/v1/list")
    public Result queryPage(AuthUserLogVO authUserLogVO){
        return authUserLogServiceImpl.getLogsList(authUserLogVO);
    }
}