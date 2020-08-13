package com.muxui.blog.common.enums;


import java.util.HashMap;
import java.util.Map;

public enum OperateEnum {
    GET_POSTS_DEFAULT("000","默认类型"),
    GET_POSTS_LIST("001","查询文章列表"),
    GET_POSTS_DETAIL("002","查询文章详情"),
    GET_POSTS_ADD("003","新增文章"),
    GET_POSTS_EDIT("004","编辑文章"),
    GET_POSTS_DELE("005","删除文章"),
    GET_TAGS_ADD("006","新增标签"),
    GET_TAGS_EDIT("007","编辑标签"),
    GET_TAGS_DELE("008","删除标签"),
    GET_TAGS_LIST("009","查询标签列表"),
    GET_TAGS_DETAIL("010","查询标签详情"),
    GET_CAREGORY_ADD("011","新增分类"),
    GET_CAREGORY_EDIT("012","编辑分类"),
    GET_CAREGORY_DELE("013","删除分类"),
    GET_CAREGORY_LIST("014","查询分类列表"),
    GET_CAREGORY_DETAIL("015","查询分类详情"),
    GET_MENU_ADD("016","新增菜单"),
    GET_MENU_EDIT("017","编辑菜单"),
    GET_MENU_DELE("018","删除菜单"),
    GET_MENU_LIST("019","查询菜单列表"),
    GET_MENU_DETAIL("020","查询菜单详情"),
    GET_LINKS_ADD("021","新增友联"),
    GET_LINKS_EDIT("022","编辑友联"),
    GET_LINKS_DELE("023","删除友联")
    ;
    private final String code;
    private final String name;

    private final static Map<String, OperateEnum> codeMap = new HashMap<>();

    static {
        for (OperateEnum operateEnum : OperateEnum.values()) {
            codeMap.put(operateEnum.code, operateEnum);
        }
    }

    OperateEnum(String code,String name) {
        this.code = code;
        this.name=name;
    }

    public String getCode() {
        return code;
    }

    public static String getName(String code) {
        OperateEnum operateEnum=codeMap.get(code);
        if (operateEnum!=null){
            return operateEnum.name;
        }
        return "";
    }

    public static OperateEnum getOperateEnum(String code){
        return codeMap.get(code);
    }
}