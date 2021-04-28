package com.oracle.kays.controller;



import com.oracle.kays.entity.Members;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

//@Controller
public class BaseController {

    //Redis缓存模板对象
   /* @Resource
    protected RedisTemplate redisTemplate;

//    @Resource
//    protected ArticleService articleService;


    protected Optional<Members> authLoginMember(String token){
        if(redisTemplate.hasKey(token)){
            Members loginMember = (Members) redisTemplate.opsForValue().get(token);
            return Optional.of(loginMember);
        }
        return Optional.empty();
    }*/

    //更新redis里的文章列表缓存
    /*protected void refreshArticleViewListInRedisCache(String catalog){
        List<ArticleView> articleViewList =null;
        Optional<List<ArticleView>> optional = articleService.queryArticleViewsByCatalog(catalog);
        if(optional.isPresent()){
            articleViewList = optional.get();
            redisTemplate.opsForValue().set("articleList_"+catalog, articleViewList);
        }
    }*/

}
