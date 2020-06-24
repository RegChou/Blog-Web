package com.muxui.blog.service.posts.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("muxui_article_attribute")
public class ArticleAttribute extends Model<ArticleAttribute> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String content;

    private Long articleId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

