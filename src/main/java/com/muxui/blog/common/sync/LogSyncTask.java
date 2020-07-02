package com.muxui.blog.common.sync;


import com.muxui.blog.service.log.domain.vo.AuthUserLogVO;
import com.muxui.blog.service.log.service.AuthUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LogSyncTask {

    @Autowired
    private AuthUserLogService sysLogServiceImpl;

    @Async(value = "asyncExecutor")
    public void addLog(AuthUserLogVO sysLog) {
        this.sysLogServiceImpl.saveLogs(sysLog);
    }
}
