package com.muxui.blog.service.dashboard.service;


import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.log.domain.vo.AuthUserLogVO;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardService {

    /**
     * 评论数量统计
     *
     * @return
     */
    Result getPostsQuantityTotal();

    /**
     * 获取浏览量折线图
     *
     * @return
     */
    Result getPostsStatistics(AuthUserLogVO authUserLogVO);

    /**
     * 获取文章排名
     *
     * @return
     */
    Result getPostsRanking(AuthUserLogVO authUserLogVO);

    Result getUserCount(AuthUserLogVO authUserLogVO);
}
