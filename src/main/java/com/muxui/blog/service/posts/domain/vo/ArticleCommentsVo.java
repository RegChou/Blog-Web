package com.muxui.blog.service.posts.domain.vo;

import com.muxui.blog.common.base.PageResult;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/7/6
 */
@Data
@Accessors(chain = true)
public class ArticleCommentsVo extends PageResult<ArticleCommentsVo> {

    private Long authorId;

    private String content;

    private Long parentId;

    private Integer status;

    private Long articleId;

    private String treePath;

    private String authorName;

    private String authorAvatar;

    private String parentUserName;

    private LocalDateTime createTime;

    private String title;
}
