package com.oracle.kays.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.oracle.kays.entity.Article;
import com.oracle.kays.view.ArticleView;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {


    @Select("select aid,title,summary,catalog_name,create_time,browse_number from article where flag=1 order by create_time desc")
    @Results(
            {
                    @Result(property = "aid", column = "aid"),
                    @Result(property = "title", column = "title"),
                    @Result(property = "summary", column = "summary"),
                    @Result(property = "catalogName", column = "catalog_name"),
                    @Result(property = "browseNumber", column = "browse_number"),
                    @Result(property = "createTime", column = "create_time")

            }
    )
    List<ArticleView> queryAllArticleViews();


    @Select("select aid,title,summary,catalog_name,create_time,browse_number from article where flag=1 and catalog=#{catalog} order by create_time desc")
    @Results(
            {
                    @Result(property = "aid", column = "aid"),
                    @Result(property = "title", column = "title"),
                    @Result(property = "summary", column = "summary"),
                    @Result(property = "catalogName", column = "catalog_name"),
                    @Result(property = "browseNumber", column = "browse_number"),
                    @Result(property = "createTime", column = "create_time")

            }
    )
    List<ArticleView> queryArticleViewsByCatalog(String catalog);

}
