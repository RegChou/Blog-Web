package com.muxui.blog.service.posts.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muxui.blog.common.base.PageResult;
import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.common.util.Markdown2HtmlUtil;
import com.muxui.blog.common.util.PageUtil;
import com.muxui.blog.common.util.PreviewTextUtils;
import com.muxui.blog.common.util.SessionUtil;
import com.muxui.blog.service.auth.domain.vo.UserSessionVO;
import com.muxui.blog.service.category.dao.TagsDao;
import com.muxui.blog.service.category.domain.Tags;
import com.muxui.blog.service.category.domain.vo.TagsVO;
import com.muxui.blog.service.posts.dao.ArticleAttributeDao;
import com.muxui.blog.service.posts.dao.ArticleDao;
import com.muxui.blog.service.posts.dao.InTagsDao;
import com.muxui.blog.service.posts.domain.Article;
import com.muxui.blog.service.posts.domain.ArticleAttribute;
import com.muxui.blog.service.posts.domain.InTags;
import com.muxui.blog.service.posts.domain.vo.PostsVO;
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
import java.util.stream.Collectors;

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
    public Result saveArticle(PostsVO postsVO) {
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        String html = Markdown2HtmlUtil.html(postsVO.getContent());
        Article article = new Article();
        assert userSessionInfo != null;
        article.setTitle(postsVO.getTitle()).setThumbnail(postsVO.getThumbnail()).setStatus(postsVO.getStatus()).
                setSummary(PreviewTextUtils.getText(html, 126)).setIsComment(postsVO.getIsComment())
                .setAuthorId(userSessionInfo.getId()).setCategoryId(postsVO.getCategoryId()).setWeight(postsVO.getWeight());
        articleDao.insert(article);
        postsVO.setId(article.getId());
        articleAttributeDao.insert(new ArticleAttribute().setContent(postsVO.getContent()).setArticleId(article.getId()));
        List<TagsVO> tagsList = postsVO.getTagsList();
        if (!CollectionUtils.isEmpty(tagsList)) {
            tagsList.forEach(tagsVO -> {
                if (tagsVO.getId() == null) {
                    Tags tags = new Tags().setName(tagsVO.getName());// saveMenu
                    tagsDao.insert(tags);
                    tagsVO.setId(tags.getId());
                }
                inTagsDao.insert(new InTags().setArticleId(article.getId()).setTagsId(tagsVO.getId()));
            });
        }
        return Result.SUCCESS();
    }

    @Override
    public Result getArticle(Long id) {
        Article article = articleDao.selectOneById(id);
        if (article == null){
            return new Result(ResultCode.DATA_NO_EXIST);
        }
        PostsVO postsVO = new PostsVO();
        postsVO.setId(article.getId())
                .setCreateTime(article.getCreateTime())
                .setSummary(article.getSummary())
                .setTitle(article.getTitle())
                .setThumbnail(article.getThumbnail())
                .setIsComment(article.getIsComment())
                .setViews(article.getViews())
                .setComments(article.getComments())
                .setCategoryId(article.getCategoryId())
                .setWeight(article.getWeight())
                .setCategoryName(article.getCategoryName());
        ArticleAttribute articleAttribute = articleAttributeDao.selectOne(new LambdaQueryWrapper<ArticleAttribute>().eq(ArticleAttribute::getArticleId, article.getId()));
        if (articleAttribute != null) {
            postsVO.setContent(articleAttribute.getContent());
        }
        List<InTags> postsTagsList = inTagsDao.selectList(new LambdaQueryWrapper<InTags>().eq(InTags::getArticleId, article.getId()));
        List<TagsVO> tagsVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(postsTagsList)) {
            postsTagsList.forEach(postsTags -> {
                Tags tags = tagsDao.selectById(postsTags.getTagsId());
                tagsVOList.add(new TagsVO().setId(tags.getId()).setName(tags.getName()));
            });
        }

        postsVO.setTagsList(tagsVOList);

        articleDao.incrementView(article.getId());
        return new Result(ResultCode.SUCCESS,postsVO);
    }

    @Override
    public Result updateArticle(PostsVO postsVO) {
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        String html = Markdown2HtmlUtil.html(postsVO.getContent());
        Article article = articleDao.selectOne(new LambdaQueryWrapper<Article>().eq(Article::getId, postsVO.getId()));
        if (article == null) {
            return new Result(ResultCode.DATA_NO_EXIST);
        }
        assert userSessionInfo != null;
        article.setTitle(postsVO.getTitle()).setThumbnail(postsVO.getThumbnail()).setStatus(postsVO.getStatus())
                .setSummary(PreviewTextUtils.getText(html, 126)).setIsComment(postsVO.getIsComment())
                .setAuthorId(userSessionInfo.getId()).setCategoryId(postsVO.getCategoryId()).setWeight(postsVO.getWeight());
        articleDao.updateById(article);
        Wrapper<ArticleAttribute> wrapper = new LambdaUpdateWrapper<ArticleAttribute>().eq(ArticleAttribute::getArticleId, article.getId());
        if (articleAttributeDao.selectCount(wrapper) > 0) {
            articleAttributeDao.update(new ArticleAttribute().setContent(postsVO.getContent()), wrapper);
        } else {
            articleAttributeDao.insert(new ArticleAttribute().setContent(postsVO.getContent()).setArticleId(article.getId()));
        }
        List<TagsVO> tagsList = postsVO.getTagsList();

        if (!CollectionUtils.isEmpty(tagsList)) {
            List<InTags> originalList = inTagsDao.selectList(new LambdaQueryWrapper<InTags>().eq(InTags::getArticleId, article.getId()));
            List<InTags> categoryTagsList = originalList.stream().filter(inTags -> !postsVO.getTagsList().stream().map(PageResult::getId).collect(Collectors.toList())
                    .contains(inTags.getTagsId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(categoryTagsList)) {
                categoryTagsList.forEach(categoryTags -> {
                    inTagsDao.deleteById(categoryTags.getId());
                });
            }
            tagsList.forEach(tagsVO -> {
                if (tagsVO.getId() == null) {
                    Tags tags = new Tags().setName(tagsVO.getName());
                    tagsDao.insert(tags);
                    tagsVO.setId(tags.getId());
                    inTagsDao.insert(new InTags().setArticleId(article.getId()).setTagsId(tagsVO.getId()));
                } else {
                    InTags inTags = inTagsDao.selectOne(new LambdaQueryWrapper<InTags>().eq(InTags::getArticleId, article.getId()).eq(InTags::getTagsId, tagsVO.getId()));
                    if (inTags == null) {
                        inTagsDao.insert(new InTags().setArticleId(article.getId()).setTagsId(tagsVO.getId()));
                    }
                }
            });
        }else {
            inTagsDao.delete(new LambdaQueryWrapper<InTags>().eq(InTags::getArticleId, article.getId()));
        }
        return Result.SUCCESS();
    }

    @Override
    public Result deleteArticle(Long id) {
        Article article = articleDao.selectById(id);
        if (article == null){
            return new Result(ResultCode.DATA_NO_EXIST);
        }
        articleDao.deleteById(id);
        articleAttributeDao.delete(new LambdaUpdateWrapper<ArticleAttribute>().eq(ArticleAttribute::getArticleId, id));
        inTagsDao.delete(new LambdaUpdateWrapper<InTags>().eq(InTags::getArticleId, id));
        return Result.SUCCESS();
    }

    @Override
    public Result updateArticleStatus(PostsVO postsVO) {
        articleDao.updateById(new Article().setId(postsVO.getId()).setStatus(postsVO.getStatus()));
        return Result.SUCCESS();
    }

    @Override
    public Result getPostsList(PostsVO postsVO) {
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
    public Result getArchiveTotalByDateList(PostsVO postsVO) {
        List<PostsVO> postsVOList = articleDao.selectArchiveTotalGroupDateList();
        postsVOList.forEach(obj -> {
            // 查询每一个时间点中的文章
            obj.setArchivePosts(articleDao.selectByArchiveDate(obj.getArchiveDate()));
        });
        return new Result(ResultCode.SUCCESS,postsVOList);
    }
}
