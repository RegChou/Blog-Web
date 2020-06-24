package com.muxui.blog.common.base;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageInfo {

    private long page = 1;
    private long size = 10;
    private Long total;
}
