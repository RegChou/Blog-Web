package com.muxui.blog.service.category.domain.vo;


import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class TagsVO{

    /**
     * 名称
     */
    private String name;

    /**
     * 文章总数
     */
    private Integer postsTotal;
}
