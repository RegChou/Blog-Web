package com.muxui.blog.service.posts.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muxui.blog.common.base.Constants;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.common.util.PageUtil;
import com.muxui.blog.common.util.SessionUtil;
import com.muxui.blog.service.auth.dao.AuthUserDao;
import com.muxui.blog.service.auth.domain.AuthUser;
import com.muxui.blog.service.auth.domain.vo.UserSessionVO;
import com.muxui.blog.service.posts.dao.ArticleCommentsDao;
import com.muxui.blog.service.posts.dao.ArticleDao;
import com.muxui.blog.service.posts.domain.ArticleComments;
import com.muxui.blog.service.posts.domain.vo.ArticleCommentsVo;
import com.muxui.blog.service.posts.service.ArticleCommentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/7/6
 */
@Service
@Slf4j
public class ArticleCommentsServiceImpl extends ServiceImpl<ArticleCommentsDao, ArticleComments> implements ArticleCommentsService {

    @Autowired
    private ArticleCommentsDao articleCommentsDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private AuthUserDao authUserDao;

    @Override
    public Result saveArticleComments(ArticleCommentsVo articleCommentsVo) {
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        ArticleComments articleComments = new ArticleComments();
        assert userSessionInfo != null;
        articleComments.setAuthorId(userSessionInfo.getId());
        articleComments.setContent(articleCommentsVo.getContent());
        articleComments.setArticleId(articleCommentsVo.getArticleId());
        articleComments.setCreateTime(LocalDateTime.now());
        String treePath;
        if (articleCommentsVo.getParentId() == null) {
            this.articleCommentsDao.insert(articleComments);
            treePath = articleComments.getId() + Constants.TREE_PATH;
        } else {
            ArticleComments parentComments = this.articleCommentsDao.selectById(articleCommentsVo.getParentId());
            if (parentComments == null) {
                return new Result(ResultCode.DATA_NO_EXIST);
            }
            articleComments.setParentId(articleCommentsVo.getParentId());
            this.articleCommentsDao.insert(articleComments);
            treePath = parentComments.getTreePath() + articleComments.getId() + Constants.TREE_PATH;
        }
        this.articleCommentsDao.updateById(articleComments.setTreePath(treePath));
        this.articleDao.incrementComments(articleCommentsVo.getArticleId());
        return Result.SUCCESS();
    }

    @Override
    public Result getArticleCommentsByArticleIdList(ArticleCommentsVo articleCommentsVo) {
        Page page = Optional.ofNullable(PageUtil.checkAndInitPage(articleCommentsVo)).orElse(PageUtil.initPage());
        List<ArticleCommentsVo> postsCommentsVOLis = this.articleCommentsDao.selectArticleCommentsByArticleIdList(page, articleCommentsVo.getArticleId());
        return new Result(ResultCode.SUCCESS, postsCommentsVOLis, PageUtil.initPageInfo(page));
    }

    @Override
    public Result getArticleCommentsList(ArticleCommentsVo articleCommentsVo) {
        Page page = Optional.ofNullable(PageUtil.checkAndInitPage(articleCommentsVo)).orElse(PageUtil.initPage());
        List<ArticleCommentsVo> postsCommentsVOLis = this.articleCommentsDao.selectArticleCommentsList(page, articleCommentsVo);
        return new Result(ResultCode.SUCCESS, postsCommentsVOLis, PageUtil.initPageInfo(page));
    }

    @Override
    public Result deleteArticleComments(Long id) {
        this.articleCommentsDao.deleteById(id);
        return Result.SUCCESS();
    }

    @Override
    public Result getArticleComment(Long id) {
        if (id == null) {
            return new Result(ResultCode.PARAM_INCORRECT);
        }
        List<ArticleCommentsVo> articleCommentsVOLis = this.articleCommentsDao.selectArticleCommentsList(new ArticleCommentsVo().setId(id));
        if (articleCommentsVOLis != null && articleCommentsVOLis.size() > 0) {
            return new Result(ResultCode.SUCCESS, articleCommentsVOLis.get(0));
        }
        return new Result(ResultCode.PARAM_INCORRECT);
    }

    @Override
    public Result replyComments(ArticleCommentsVo articleCommentsVo) {
        AuthUser authUser = authUserDao.selectAdmin();
        ArticleComments postsComments = articleCommentsDao.selectById(articleCommentsVo.getParentId())
                .setParentId(articleCommentsVo.getParentId())
                .setContent(articleCommentsVo.getContent())
                .setAuthorId(authUser.getId())
                .setCreateTime(LocalDateTime.now());
        this.articleCommentsDao.insert(postsComments);
        String treePath = postsComments.getTreePath() + postsComments.getId() + Constants.TREE_PATH;
        this.articleCommentsDao.updateById(postsComments.setTreePath(treePath));
        this.articleDao.incrementComments(articleCommentsVo.getArticleId());
        return Result.SUCCESS();
    }
}
