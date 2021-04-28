package com.oracle.kays.service;


import com.oracle.kays.entity.Article;
import com.oracle.kays.mapper.ArticleMapper;
import com.oracle.kays.repository.ArticleRepository;
import com.oracle.kays.view.ArticleView;

import java.util.List;
import java.util.Optional;

public interface ArticleService extends BaseService<Article,Integer, ArticleRepository, ArticleMapper> {

    //根据文章编号查询文章资料
    Optional<Article> queryArticleByAid(int aid);

    //根据版块查询所有文章资料
    Optional<List<Article>> queryArticlesByCatalog(String catalog);

    //保存文章
    boolean saveArticle(Article article);

    //更新文章
    boolean updateArticle(Article article);

    //查询最新发表的前4篇博文
    Optional<List<Article>> queryTopNumberArticles(int number);


    //根据版块查询所有文章视图资料
    Optional<List<ArticleView>> queryArticleViewsByCatalog(String catalog);
}
