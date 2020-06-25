package com.muxui.blog.service.posts.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Tags;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("muxui_in_tags")
public class InTags extends Model<InTags> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标签主键
     */
    private Long tagsId;

    /**
     * 文章主键
     */
    private Long articleId;


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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
