package com.muxui.blog.service.posts.domain.vo;


import com.muxui.blog.common.base.PageResult;
import com.muxui.blog.service.category.domain.vo.TagsVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class PostsVO extends PageResult<PostsVO> {


    /**
     * 主键
     */
    protected Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 封面图
     */
    private String thumbnail;

    /**
     * 评论数
     */
    private Integer comments;

    /**
     * 状态 1 草稿 2 发布
     */
    private Integer status;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 文章权重
     */
    private Integer weight;

    /**
     * 外链地址
     */
    private String sourceUri;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 标签列表
     */
    private List<String> tagList;

    /**
     * 是否打开评论
     */
    private Integer isComment;


    private String author;

    private List<TagsVO> tagsList;

    private Integer platformType;

    private Integer commentsTotal;

    private Integer viewsTotal;

    private Integer articleTotal;

    private Integer draftTotal;

    private Integer publishTotal;

    private Integer syncTotal;

    private Integer todayPublishTotal;

    private LocalDateTime archiveDate;

    /**
     * 社交账户ID
     */
    private String socialId;

    private Integer year;

    private String tagsName;

    // 文章分类Id
    private Integer categoryId;

    // 是否根据权重排序 0：否 1 是
    private Integer isWeight;

    /**
     * 文章分类名称
     */
    private String categoryName;

    /**
     * 用于归档时保存文章自身列表
     */
    private List<PostsVO> archivePosts;

    /**
     * 标签ID
     */
    private String tagsId;
}
