package com.muxui.blog.service.dashboard.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.common.enums.ArticleStatusEnum;
import com.muxui.blog.common.enums.DateTypeEnum;
import com.muxui.blog.common.util.DateUtil;
import com.muxui.blog.common.util.PageUtil;
import com.muxui.blog.service.auth.dao.AuthUserDao;
import com.muxui.blog.service.auth.domain.AuthUser;
import com.muxui.blog.service.auth.domain.vo.AuthUserVO;
import com.muxui.blog.service.dashboard.service.DashboardService;
import com.muxui.blog.service.log.dao.AuthUserLogDao;
import com.muxui.blog.service.log.domain.vo.AuthUserLogVO;
import com.muxui.blog.service.posts.dao.ArticleDao;
import com.muxui.blog.service.posts.domain.Article;
import com.muxui.blog.service.posts.domain.vo.PostsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private AuthUserLogDao authUserLogDao;

    @Autowired
    AuthUserDao authUserDao;

    @Override
    public Result getPostsQuantityTotal() {
        PostsVO postsVO = Optional.ofNullable(articleDao.selectPostsTotal()).orElse(new PostsVO().setViewsTotal(0).setCommentsTotal(0));
        Integer article = articleDao.selectCount(null);
        postsVO.setArticleTotal(article);
        Integer draft = articleDao.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getStatus, ArticleStatusEnum.DRAFT.getStatus()));
        postsVO.setDraftTotal(draft);
        postsVO.setPublishTotal(article - draft);
        Integer todayPublishTotal = articleDao.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getStatus, ArticleStatusEnum.PUBLISH.getStatus())
                .between(Article::getCreateTime, LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), DateUtil.MAX)));
        postsVO.setTodayPublishTotal(todayPublishTotal);
        return new Result(ResultCode.SUCCESS,postsVO);
    }

    @Override
    public Result getPostsStatistics(AuthUserLogVO authUserLogVO) {

        getTime(authUserLogVO);

        List<AuthUserLogVO> todayList = authUserLogDao.selectPostsListStatistics(
                LocalDateTime.of(authUserLogVO.getStartTime().toLocalDate(), LocalTime.MIN),
                LocalDateTime.of(authUserLogVO.getEndTime().toLocalDate(), DateUtil.MAX),
                authUserLogVO.getType());
        List<AuthUserLogVO> yesterdayList = authUserLogDao.selectPostsListStatistics(
                LocalDateTime.of(authUserLogVO.getStartTime().toLocalDate().minusDays(1), LocalTime.MIN),
                LocalDateTime.of(authUserLogVO.getEndTime().toLocalDate().minusDays(1), DateUtil.MAX),
                authUserLogVO.getType());
        todayList.addAll(yesterdayList);

        DateTypeEnum dateTypeEnum = DateTypeEnum.valueOf(authUserLogVO.getType().toUpperCase());
        List<AuthUserLogVO> chartVO = new ArrayList<>();

        switch (dateTypeEnum) {
            case DAY:
                getDate(LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN),
                        48, dateTypeEnum, chartVO, todayList);
                break;
            case WEEK:
                getDate(LocalDateTime.of(LocalDate.now().with(DayOfWeek.of(1)), LocalTime.MIN),
                        7, dateTypeEnum, chartVO, todayList);
                break;
            case MONTH:
                int curMonth = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                getDate(LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN),
                        curMonth, dateTypeEnum, chartVO, todayList);

                int preMonth = LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                getDate(LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
                        LocalTime.MIN).minusMonths(1), preMonth, dateTypeEnum, chartVO, todayList);
                break;
            case YEAR:
                getDate(LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).minusYears(1), LocalTime.MIN),
                        24, dateTypeEnum, chartVO, todayList);
                break;
            default:
                long days = Duration.between(authUserLogVO.getStartTime(), authUserLogVO.getEndTime()).toDays();
                getDate(LocalDateTime.of(authUserLogVO.getStartTime().toLocalDate().minusDays(1), LocalTime.MIN),
                        (int) days, dateTypeEnum, chartVO, todayList);
                break;
        }

        return new Result(ResultCode.SUCCESS , chartVO);
    }

    /**
     * 查询日期
     *
     * @param localDateTime
     * @param size
     * @param dateTypeEnum
     * @param chartVO
     * @param todayList
     */
    private void getDate(LocalDateTime localDateTime, Integer size, DateTypeEnum dateTypeEnum, List<AuthUserLogVO> chartVO, List<AuthUserLogVO> todayList) {
        for (int i = 0; i < size; i++) {
            AuthUserLogVO authUserLogVO1 = new AuthUserLogVO();
            switch (dateTypeEnum) {
                case DAY:
                    authUserLogVO1.setCreateTime(localDateTime.plusHours(i));
                    break;
                case YEAR:
                    authUserLogVO1.setCreateTime(localDateTime.plusMonths(i));
                    break;
                case WEEK:
                    authUserLogVO1.setCreateTime(localDateTime.plusDays(i));
                    authUserLogVO1.setIndex(authUserLogVO1.getCreateTime().getDayOfWeek().getValue());
                    break;
                default:
                    authUserLogVO1.setCreateTime(localDateTime.plusDays(i));
                    break;
            }

            for (AuthUserLogVO userLogVO : todayList) {
                boolean equal = userLogVO.getCreateTime().isEqual(authUserLogVO1.getCreateTime());
                if (equal) {
                    authUserLogVO1.setViewTotal(userLogVO.getViewTotal());
                    break;
                } else {
                    authUserLogVO1.setViewTotal(0);
                }
            }

            chartVO.add(authUserLogVO1);
        }
    }

    @Override
    public Result getPostsRanking(AuthUserLogVO authUserLogVO) {

        if (StringUtils.isBlank(authUserLogVO.getType())) {
            return new Result(ResultCode.PARAM_INCORRECT);
        }

        getTime(authUserLogVO);

        Page page = Optional.of(PageUtil.checkAndInitPage(authUserLogVO)).orElse(PageUtil.initPage());
        List<AuthUserLogVO> authUserLogVOList = authUserLogDao.selectPostsRanking(
                page,
                LocalDateTime.of(authUserLogVO.getStartTime().toLocalDate(), LocalTime.MIN),
                LocalDateTime.of(authUserLogVO.getEndTime().toLocalDate(), DateUtil.MAX));

        AtomicInteger start = new AtomicInteger((int) ((page.getCurrent() - 1) * page.getSize()));
        if (!CollectionUtils.isEmpty(authUserLogVOList)) {
            authUserLogVOList.forEach(authUserLogVO1 -> {
                String parameter = authUserLogVO1.getParameter();
                JSONObject jsonObject = JSONObject.parseObject(parameter);
                if (jsonObject != null) {
                    String id = (String) jsonObject.get("id");
                    Article posts = articleDao.selectById(id);
                    if (posts != null) {
                        authUserLogVO1.setTitle(posts.getTitle());
                    }

                    authUserLogVO1.setParameter(null).setIndex(start.incrementAndGet());
                }
            });
        }

        return new Result(ResultCode.SUCCESS,authUserLogVOList, PageUtil.initPageInfo(page));
    }

    private void getTime(AuthUserLogVO authUserLogVO) {
        DateTypeEnum dateTypeEnum = DateTypeEnum.valueOf(authUserLogVO.getType().toUpperCase());
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now();

        switch (dateTypeEnum) {
            case DAY:
                startTime = LocalDateTime.of(startTime.toLocalDate(), LocalTime.MIN);
                endTime = LocalDateTime.of(endTime.toLocalDate(), DateUtil.MAX);
                break;
            case WEEK:
                startTime = LocalDateTime.of(startTime.toLocalDate().with(DayOfWeek.of(1)), LocalTime.MIN);
                endTime = LocalDateTime.of(endTime.toLocalDate().with(DayOfWeek.of(7)), DateUtil.MAX);
                break;
            case MONTH:
                startTime = LocalDateTime.of(startTime.toLocalDate().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
                endTime = LocalDateTime.of(endTime.toLocalDate().with(TemporalAdjusters.lastDayOfMonth()), DateUtil.MAX);
                break;
            case YEAR:
                startTime = LocalDateTime.of(startTime.toLocalDate().with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
                endTime = LocalDateTime.of(endTime.toLocalDate().with(TemporalAdjusters.lastDayOfYear()), DateUtil.MAX);
                break;
            default:
                startTime = authUserLogVO.getStartTime();
                endTime = authUserLogVO.getEndTime();
                if (startTime == null || endTime == null) {
                    startTime = LocalDateTime.now();
                    endTime = LocalDateTime.now();
                }
                break;
        }

        authUserLogVO.setStartTime(startTime).setEndTime(endTime);
    }

    @Override
    public Result getUserCount(AuthUserLogVO authUserLogVO) {
        AuthUserVO authUserVO = new AuthUserVO();
        Integer usercount = authUserDao.selectCount(null);
        Integer todayTotal = authUserDao.selectCount(new LambdaQueryWrapper<AuthUser>()
                .between(AuthUser::getCreateTime, LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), DateUtil.MAX)));
        authUserVO.setUserCount(usercount);
        authUserVO.setToDayNew(todayTotal);
        return new Result(ResultCode.SUCCESS,authUserVO);
    }
}
