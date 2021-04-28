package com.oracle.kays.controller;


import com.oracle.kays.entity.Article;
import com.oracle.kays.json.JsonData;
import com.oracle.kays.json.JsonResult;
import com.oracle.kays.service.ArticleService;
import com.oracle.kays.service.MembersService;
import com.oracle.kays.view.ArticleView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Api("博文模块接口")
@RestController
@RequestMapping("article")
public class ArticleController{

    @Resource
    private ArticleService articleService;


    @ApiOperation("根据博文编号查询博文资料")
    @GetMapping("/{aid}")
    public JsonData queryArticleByAid(@PathVariable("aid") int aid) {
        Optional<Article> optional = articleService.queryArticleByAid(aid);
        if (optional.isPresent()) {
            return JsonResult.success(optional.get(), "查询博文资料成功！");
        }
        return JsonResult.fail("查询博文资料失败！");
    }


    @GetMapping("/catalog/{catalog}")
    @ApiOperation(value = "获取指定版块下的所有文章详情信息")
    public JsonData queryArticlesByCatalog(@PathVariable("catalog") String catalog) {
        //List<Article> articleList = null;

        List<ArticleView> articleList = null;
        try {
            //把博文列表优先放到redis缓存里面
//            if(redisTemplate.opsForValue().get("articleList_"+catalog)==null){
//                Optional<List<ArticleView>> optional = articleService.queryArticleViewsByCatalog(catalog);
//                if (optional.isPresent()) {
//                    articleList = optional.get();
//                    redisTemplate.opsForValue().set("articleList_"+catalog, articleList);
//                    //return JsonResult.success(articleList, "询博文列表成功。");
//                }else{
//                    return JsonResult.fail("查询博文列表失败。");
//                }
//            }
            Optional<List<ArticleView>> optional = articleService.queryArticleViewsByCatalog(catalog);
            if (optional.isPresent()) {
                articleList = optional.get();
//                redisTemplate.opsForValue().set("articleList_"+catalog, articleList);
                //return JsonResult.success(articleList, "询博文列表成功。");
            }else{
                return JsonResult.fail("查询博文列表失败。");
            }

//            articleList = (List<ArticleView>) redisTemplate.opsForValue().get("articleList_"+catalog);
            return JsonResult.success(articleList, "询博文列表成功。");
        } catch (Exception ex) {
            ex.printStackTrace();
            return JsonResult.fail("查询博文列表失败。");
        }
    }

    @PostMapping("/")
    @ApiOperation("保存新增文章")
    public JsonData saveArticle(@RequestBody Article article) {
        try {

            if (articleService.saveArticle(article)) {

                //更新redis缓存
//                refreshArticleViewListInRedisCache("all");
//                refreshArticleViewListInRedisCache(article.getCatalog());

                return JsonResult.success(null, "发表文章成功！");
            }
            return JsonResult.fail("发表文章失败！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return JsonResult.fail("发表文章失败！");
        }
    }

    @PutMapping("/")
    @ApiOperation("更新文章")
    public JsonData updateArticle(@RequestBody Article article) {
        try {

            if (articleService.updateArticle(article)) {

                //更新redis缓存
//                refreshArticleViewListInRedisCache("all");
//                refreshArticleViewListInRedisCache(article.getCatalog());

                return JsonResult.success(null, "更新文章成功！");
            }
            return JsonResult.fail("更新文章失败！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return JsonResult.fail("更新文章失败！");
        }
    }

    @GetMapping("/top/{num}")
    @ApiOperation("查询最新更新的前num篇文章")
    public JsonData queryTopNumberArticle(@PathVariable("num") int number){
        Optional<List<Article>> optional = articleService.queryTopNumberArticles(number);
        if(optional.isPresent()){
            return JsonResult.success(optional.get(),"查询最新文章列表成功！");
        }
        return JsonResult.fail("查询最新文章列表失败！");
    }
}
