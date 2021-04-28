package com.oracle.kays.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.oracle.kays.entity.Article;
import com.oracle.kays.mapper.ArticleMapper;
import com.oracle.kays.repository.ArticleRepository;
import com.oracle.kays.service.ArticleService;
import com.oracle.kays.view.ArticleView;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article,Integer, ArticleRepository, ArticleMapper> implements ArticleService {


    @Override
    public Optional<Article> queryArticleByAid(int aid) {

        return selectById(aid);
        //或者
        //return findById(aid);
    }


    @Override
    public Optional<List<Article>> queryArticlesByCatalog(String catalog) {
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>();
        wrapper.orderByDesc("create_time"); //根据博文发表时间降序排序
        if(!"all".equalsIgnoreCase(catalog)) {
            //wrapper = new QueryWrapper<Article>();

            wrapper.eq("catalog", catalog);
        }
        return selectList(wrapper);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean saveArticle(Article article) {

        return insert(article);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean updateArticle(Article article) {

        return updateById(article);
    }

    @Override
    public Optional<List<Article>> queryTopNumberArticles(int number) {
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>();
        wrapper.orderByDesc("create_time");
        wrapper.last("limit "+number);
        return selectList(wrapper);
    }

    @Override
    public Optional<List<ArticleView>> queryArticleViewsByCatalog(String catalog) {
        List<ArticleView> articleViewList = null;
        if("all".equalsIgnoreCase(catalog)) {
            articleViewList = mapper.queryAllArticleViews();
        }else{
            articleViewList = mapper.queryArticleViewsByCatalog(catalog);
        }

        if(articleViewList!=null && articleViewList.size()>0){
            return Optional.of(articleViewList);
        }

        return Optional.empty();
    }
}
