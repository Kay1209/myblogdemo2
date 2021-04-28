package com.oracle.kays.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
//博客视图类
public class ArticleView extends BaseView implements Serializable {

    private int aid; //博文编号
    private String catalogName; //板块名字
    private String title; //博客标题
    private String summary; //摘要
    private int browseNumber; //浏览数量

}
