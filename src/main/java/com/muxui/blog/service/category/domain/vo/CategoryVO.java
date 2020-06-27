package com.muxui.blog.service.category.domain.vo;

import com.muxui.blog.common.base.PageResult;
import com.muxui.blog.service.category.domain.Category;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ou
 */
@Data
@Accessors(chain = true)
public class CategoryVO extends PageResult<CategoryVO> {
    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private List<TagsVO> tagsList;

    private Integer total;
}
