package com.muxui.blog.service.category.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muxui.blog.service.category.domain.Tags;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author ouyang
 * @since 2020-06-23
 */
@Repository
public interface TagsDao extends BaseMapper<Tags> {

}
