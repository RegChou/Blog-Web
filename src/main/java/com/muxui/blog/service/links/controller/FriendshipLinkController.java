package com.muxui.blog.service.links.controller;


import com.muxui.blog.common.annotation.LoginRequired;
import com.muxui.blog.common.annotation.OperateLog;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.enums.OperateEnum;
import com.muxui.blog.common.util.ThrowableUtils;
import com.muxui.blog.service.links.domain.vo.FriendshipLinkVO;
import com.muxui.blog.service.links.service.FriendshipLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/link")
public class FriendshipLinkController {

    @Autowired
    private FriendshipLinkService friendshipLinkService;

    @GetMapping("/v1/list")
    public Result getFriendshipLinkList(FriendshipLinkVO friendshipLinkVO) {
        return friendshipLinkService.getFriendshipLinkList(friendshipLinkVO);
    }


    @GetMapping("/v2/list")
    public Result getFriendshipLinkMap(FriendshipLinkVO friendshipLinkVO) {
        return friendshipLinkService.getFriendshipLinkMap(friendshipLinkVO);
    }

    @OperateLog(module = "新增友联", code= OperateEnum.GET_LINKS_ADD)
    @LoginRequired
    @PostMapping("/v1/add")
    public Result saveFriendshipLink(@RequestBody FriendshipLinkVO friendshipLinkVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return friendshipLinkService.saveFriendshipLink(friendshipLinkVO);
    }

    @OperateLog(module = "编辑友联", code= OperateEnum.GET_LINKS_EDIT)
    @LoginRequired
    @PutMapping("/v1/update")
    public Result updateFriendshipLink(@RequestBody FriendshipLinkVO friendshipLinkVO) {
        return friendshipLinkService.updateFriendshipLink(friendshipLinkVO);
    }

    @LoginRequired
    @GetMapping("/v1/{id}")
    public Result getFriendshipLink(@PathVariable Long id) {
        return friendshipLinkService.getFriendshipLink(id);
    }

    @OperateLog(module = "删除友联", code= OperateEnum.GET_LINKS_DELE)
    @LoginRequired
    @DeleteMapping("/v1/{id}")
    public Result deleteFriendshipLink(@PathVariable Long id) {
        return friendshipLinkService.deleteFriendshipLink(id);
    }
}

