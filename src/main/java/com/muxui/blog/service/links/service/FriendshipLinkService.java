package com.muxui.blog.service.links.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.service.links.domain.FriendshipLink;
import com.muxui.blog.service.links.domain.vo.FriendshipLinkVO;


public interface FriendshipLinkService extends IService<FriendshipLink> {

    /**
     * 获取友情链接列表
     * @return
     */
    Result getFriendshipLinkList(FriendshipLinkVO friendshipLinkVO);

    Result getFriendshipLinkMap(FriendshipLinkVO friendshipLinkVO);

    /**
     * 更新友情链接
     * @param friendshipLinkVO
     * @return
     */
    Result updateFriendshipLink(FriendshipLinkVO friendshipLinkVO);

    /**
     * 删除友情链接
     * @param id
     * @return
     */
    Result deleteFriendshipLink(Long id);

    /**
     * 新增友情链接
     * @param friendshipLinkVO
     * @return
     */
    Result saveFriendshipLink(FriendshipLinkVO friendshipLinkVO);

    Result getFriendshipLink(Long id);

}
