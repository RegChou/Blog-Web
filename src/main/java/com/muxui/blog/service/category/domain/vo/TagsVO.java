package com.muxui.blog.service.category.domain.vo;


import com.muxui.blog.common.base.PageResult;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class TagsVO  extends PageResult<TagsVO> {

    /**
     * 名称
     */
    private String name;

    /**
     * 文章总数
     */
    private Integer articleTotal;
}
