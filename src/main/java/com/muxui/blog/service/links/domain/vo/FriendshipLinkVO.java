package com.muxui.blog.service.links.domain.vo;


import com.muxui.blog.common.base.PageResult;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class FriendshipLinkVO extends PageResult<FriendshipLinkVO> {

    private Long id;

    private String title;

    private String name;

    private String href;

    private String logo;

    private Integer sort;

    private String description;


}
