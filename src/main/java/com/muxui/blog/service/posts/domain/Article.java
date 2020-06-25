package com.muxui.blog.service.posts.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("muxui_article")
public class Article extends Model<Article> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章标题
     */
    private String title;

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
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long authorId;

    /**
     * 是否打开评论
     */
    private Integer isComment;

    /**
     * 文章分类Id
     */
    private Integer categoryId;

    /**
     * 文章分类名称
     */
    @TableField(exist = false)
    private String categoryName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

