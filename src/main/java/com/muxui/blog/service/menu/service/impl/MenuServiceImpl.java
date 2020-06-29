package com.muxui.blog.service.menu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.common.util.PageUtil;
import com.muxui.blog.service.menu.dao.MenuDao;
import com.muxui.blog.service.menu.domain.Menu;
import com.muxui.blog.service.menu.domain.vo.MenuVO;
import com.muxui.blog.service.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public Result saveMenu(MenuVO menuVO) {
        menuDao.insert(new Menu()
                .setIcon(menuVO.getIcon())
                .setParentId(menuVO.getParentId())
                .setSort(menuVO.getSort())
                .setTitle(menuVO.getTitle())
                .setUrl(menuVO.getUrl()));
        return Result.SUCCESS();
    }

    @Override
    public Result getMenu(Long id) {
        Menu menu=menuDao.selectById(id);
        if (menu==null){
           return new Result(ResultCode.DATA_NO_EXIST);
        }
        return new Result(ResultCode.SUCCESS,menu);
    }

    @Override
    public Result getMenuList(MenuVO menuVO) {
        menuVO= Optional.ofNullable(menuVO).orElse(new MenuVO());
        Page page= Optional.of(PageUtil.checkAndInitPage(menuVO)).orElse(PageUtil.initPage());
        if (!StringUtils.isEmpty(menuVO.getKeywords())){
            menuVO.setKeywords("%"+menuVO.getKeywords()+"%");
        }
        List<MenuVO> menuVOList=menuDao.selectMenuList(page,menuVO);
        return new Result(ResultCode.SUCCESS,menuVOList, PageUtil.initPageInfo(page));
    }

    @Override
    public Result updateMenu(MenuVO menuVO) {
        this.menuDao.updateById(
                new Menu()
                .setId(menuVO.getId())
                .setIcon(menuVO.getIcon())
                .setTitle(menuVO.getTitle())
                .setParentId(menuVO.getParentId())
                .setSort(menuVO.getSort())
                .setUrl(menuVO.getUrl())
        );
        return Result.SUCCESS();
    }

    @Override
    public Result deleteMenu(Long id) {
        this.menuDao.deleteById(id);
        return Result.SUCCESS();
    }

    @Override
    public Result getFrontMenuList(MenuVO menuVO) {
        List<Menu> menus = menuDao.selectMenuList(menuVO.setParentId(0L));
        List<MenuVO> menuVOS=new ArrayList<>();
        menus.forEach(menu -> {
            menuVOS.add(new MenuVO()
                    .setId(menu.getId())
                    .setIcon(menu.getIcon())
                    .setTitle(menu.getTitle())
                    .setParentId(menu.getParentId())
                    .setSort(menu.getSort())
                    .setUrl(menu.getUrl())
                    .setChild(menuDao.selectMenuList(menuVO.setParentId(menu.getId())))
            );
        });
        return new Result(ResultCode.SUCCESS,menuVOS);
    }
}
