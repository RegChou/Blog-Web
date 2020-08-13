package com.muxui.blog.service.menu.controller;



import com.muxui.blog.common.annotation.OperateLog;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.enums.OperateEnum;
import com.muxui.blog.service.menu.domain.vo.MenuVO;
import com.muxui.blog.service.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单表: 后端controller类
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuController {
    
    
    @Autowired
    private MenuService menuService;
    
    
    /**
     * 新增菜单表
     */
    @OperateLog(module = "新增菜单", code= OperateEnum.GET_MENU_ADD)
    @PostMapping("/v1/add")
    public Result saveMenu(@RequestBody MenuVO menuVO){
        return menuService.saveMenu(menuVO);
    }
    
    
    /**
     * 查询菜单表
     */
    @OperateLog(module = "查询菜单详情", code= OperateEnum.GET_MENU_DETAIL)
    @GetMapping("/v1/{id}")
    public Result getMenu(@PathVariable Long id){
        return menuService.getMenu(id);
    }
    
    
    /**
     * 分页查询菜单表
     */
    @OperateLog(module = "查询菜单列表", code= OperateEnum.GET_MENU_LIST)
    @GetMapping("/v1/list")
    public Result getMenuList(MenuVO menuVO){
        return menuService.getMenuList(menuVO);
    }


    @GetMapping("/front/v1/list")
    public Result getFrontMenuList(MenuVO menuVO){
        return menuService.getFrontMenuList(menuVO);
    }
    
    /**
     * 更新菜单表
     */
    @OperateLog(module = "编辑菜单", code= OperateEnum.GET_MENU_EDIT)
    @PutMapping("/v1/update")
    public Result updateMenu(@RequestBody MenuVO menuVO){
        return menuService.updateMenu(menuVO);
    }

    /**
     * 删除菜单表
     */
    @OperateLog(module = "删除菜单", code= OperateEnum.GET_MENU_DELE)
    @DeleteMapping("/v1/{id}")
    public Result delete(@PathVariable Long id){
        return menuService.deleteMenu(id);
    }
}