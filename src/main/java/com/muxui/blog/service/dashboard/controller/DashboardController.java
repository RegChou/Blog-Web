package com.muxui.blog.service.dashboard.controller;

import com.muxui.blog.common.annotation.LoginRequired;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.dashboard.service.DashboardService;
import com.muxui.blog.service.log.domain.vo.AuthUserLogVO;
import com.muxui.blog.service.posts.domain.vo.PostsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @LoginRequired
    @GetMapping("/blog-total/v1/quantity")
    public Result getPostsQuantityTotal(PostsVO postsVO) {
        return dashboardService.getPostsQuantityTotal();
    }

    @LoginRequired
    @GetMapping("/post-statistics/v1/list")
    public Result getPostsStatistics(@Valid AuthUserLogVO authUserLogVO) {
        return dashboardService.getPostsStatistics(authUserLogVO);
    }

    @LoginRequired
    @GetMapping("/post-ranking/v1/list")
    public Result getPostsRanking(@Valid AuthUserLogVO authUserLogVO) {
        return dashboardService.getPostsRanking(authUserLogVO);
    }

    @LoginRequired
    @GetMapping("/user/v1/count")
    public Result getUserCount(@Valid AuthUserLogVO authUserLogVO) {
        return dashboardService.getUserCount(authUserLogVO);
    }

}
