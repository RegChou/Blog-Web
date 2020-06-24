package com.muxui.blog.service.posts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.common.util.Markdown2HtmlUtil;
import com.muxui.blog.common.util.PageUtil;
import com.muxui.blog.common.util.PreviewTextUtils;
import com.muxui.blog.common.util.SessionUtil;
import com.muxui.blog.service.auth.domain.vo.UserSessionVO;
import com.muxui.blog.service.category.dao.TagsDao;
import com.muxui.blog.service.category.domain.Tags;
import com.muxui.blog.service.posts.dao.ArticleAttributeDao;
import com.muxui.blog.service.posts.dao.ArticleDao;
import com.muxui.blog.service.posts.dao.InTagsDao;
import com.muxui.blog.service.posts.domain.Article;
import com.muxui.blog.service.posts.domain.ArticleAttribute;
import com.muxui.blog.service.posts.domain.InTags;
import com.muxui.blog.service.posts.domain.vo.PostsVO;
import com.muxui.blog.service.posts.domain.vo.TagsVO;
import com.muxui.blog.service.posts.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/6/22
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Autowired
    ArticleAttributeDao articleAttributeDao;

    @Autowired
    InTagsDao inTagsDao;

    @Autowired
    TagsDao tagsDao;

    @Override
    public Result getFetchList(PostsVO postsVO) {
        postsVO = Optional.ofNullable(postsVO).orElse(new PostsVO());
        Page page = Optional.of(PageUtil.checkAndInitPage(postsVO)).orElse(PageUtil.initPage());
        if (StringUtils.isNotBlank(postsVO.getKeywords())) {
            postsVO.setKeywords("%" + postsVO.getKeywords() + "%");
        }
        if (StringUtils.isNoneBlank(postsVO.getTitle())) {
            postsVO.setTitle("%" + postsVO.getTitle() + "%");
        }
        List<PostsVO> postsVOList = articleDao.selectPostsList(page, postsVO);
        if (!CollectionUtils.isEmpty(postsVOList)) {
            postsVOList.forEach(postsVO1 -> {
                List<InTags> postsTagsList = inTagsDao.selectList(new LambdaQueryWrapper<InTags>().eq(InTags::getArticleId, postsVO1.getId()));
                List<TagsVO> tagsVOList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(postsTagsList)) {
                    postsTagsList.forEach(postsTags -> {
                        Tags tags = tagsDao.selectById(postsTags.getTagsId());
                        tagsVOList.add(new TagsVO().setId(tags.getId()).setName(tags.getName()));
                    });
                }
                postsVO1.setTagsList(tagsVOList);
            });
        }
        return new Result(ResultCode.SUCCESS,postsVOList, PageUtil.initPageInfo(page));
    }

    @Override
    public Result savePosts(PostsVO postsVO) {
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        String html = Markdown2HtmlUtil.html(postsVO.getContent());
        Article article = new Article();
        LocalDateTime dateTime = Optional.ofNullable(postsVO.getCreateTime()).orElse(LocalDateTime.now());
        article.setTitle(postsVO.getTitle()).setCreateTime(dateTime)
                .setUpdateTime(dateTime).setThumbnail(postsVO.getThumbnail());
        assert userSessionInfo != null;
        article.setStatus(postsVO.getStatus()).setSummary(PreviewTextUtils.getText(html, 126)).setIsComment(postsVO.getIsComment())
                .setAuthorId(userSessionInfo.getId()).setCategoryId(postsVO.getCategoryId()).setWeight(postsVO.getWeight());
        articleDao.insert(article);
        articleAttributeDao.insert(new ArticleAttribute().setContent(postsVO.getContent()).setArticleId(article.getId()));
        List<TagsVO> tagsList = postsVO.getTagsList();
        if (!CollectionUtils.isEmpty(tagsList)) {
            tagsList.forEach(tagsVO -> {
                if (tagsVO.getId() == null) {
                    Tags tags = new Tags().setName(tagsVO.getName()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now());// saveMenu
                    tagsDao.insert(tags);
                    tagsVO.setId(tags.getId());
                }
                inTagsDao.insert(new InTags().setArticleId(article.getId()).setTagsId(tagsVO.getId()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now()));
            });
        }
        return Result.SUCCESS();
    }
}
