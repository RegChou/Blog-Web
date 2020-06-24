package com.muxui.blog.common.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.muxui.blog.common.base.PageInfo;
import com.muxui.blog.common.base.PageResult;


public class PageUtil {

    public static Page checkAndInitPage(PageResult pageResult) {
        if (pageResult.getPage() == null) {
            pageResult.setPage(1);
        }
        if (pageResult.getSize() == null){
            pageResult.setSize(10);
        }

        return new Page(pageResult.getPage(), pageResult.getSize());
    }

    public static Page initPage() {
        return new Page(1, 10);
    }

    public static PageInfo initPageInfo(Page page) {
        if (page != null) {
            return new PageInfo().setPage(page.getCurrent()).setSize(page.getSize()).setTotal(page.getTotal());
        }
        return null;
    }
}
