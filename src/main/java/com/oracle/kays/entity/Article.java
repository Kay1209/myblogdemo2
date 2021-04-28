package com.oracle.kays.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ARTICLE")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@Data
@NoArgsConstructor
@AllArgsConstructor
//博文表
public class Article extends BaseEntity implements Serializable,Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Integer aid; //帖子编号(博文编号)
    private int uid; //发帖子用户编号
    @Column(length = 50)
    private String username; //发帖子的用户名
    @Column(length = 50)
    private String title; //帖子的标题
    private String summary; //文章摘要
    private String mdcontent; //markdown正文用于编辑时使用
    private String content; //帖子的正文,html格式
    private int replyNumber; //回复的数量
    private int browseNumber; //浏览数量
    @Column(length = 50)
    private String tags; //帖子的标签
    @Column(length = 50)
    private String type; //帖子的类型
    @Column(length = 50)
    private String catalog; //帖子所属的板块id。
    @Column(length = 50)
    private String catalogName; //帖子版块的名称

}
