package com.muxui.blog.service.menu.domain.vo;


import com.muxui.blog.common.base.PageResult;
import com.muxui.blog.service.menu.domain.Menu;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
public class MenuVO extends PageResult<MenuVO> {

	private static final long serialVersionUID = 1L;

    // columns START
	private Long id; 

	/**
	 * 父菜单Id
	 */
	private Long parentId;

	/**
	 * 名称
	 */
	private String title; 

	/**
	 * icon图标
	 */
	private String icon; 

	/**
	 * 跳转路径
	 */
	private String url; 

	/**
	 * 排序
	 */
	private Integer sort;

	private List<Menu> child;
	// columns END
}