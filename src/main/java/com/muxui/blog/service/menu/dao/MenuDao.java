package com.muxui.blog.service.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.muxui.blog.service.menu.domain.Menu;
import com.muxui.blog.service.menu.domain.vo.MenuVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单表:数据层
 */
@Repository
public interface MenuDao extends BaseMapper<Menu> {
    
    /**
     * 分页查询菜单表
     */
    List<Menu> selectMenuList(@Param("page") Page<Menu> page, @Param("condition") MenuVO menu);
    List<Menu> selectMenuList(@Param("condition") MenuVO menu);
}
