package com.muxui.blog.common.base;


import lombok.Data;
import lombok.experimental.Accessors;



@Data
@Accessors(chain = true)
public class PageResult<T> {

    /**
     * 主键
     */
    protected Long id;
    /**
     * 关键词搜索
     */
    protected String keywords;
    /**
     * 页数
     */
    protected Integer page;
    /**
     * 每页大小
     */
    protected Integer size;

    public Long getId() {
        return id;
    }

    public T setId(Long id) {
        this.id = id;
        return (T) this;
    }

    public Integer getSize() {
        return size;
    }

    public PageResult<T> setSize(Integer size) {
        this.size = size > 20 ? 20 : size;
        return this;
    }
}
