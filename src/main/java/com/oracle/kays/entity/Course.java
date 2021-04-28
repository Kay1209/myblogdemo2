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
@Table(name="COURSE")
@JsonIgnoreProperties(value ={ "hibernateLazyInitializer"})
@Data
@NoArgsConstructor
@AllArgsConstructor

//课程表
public class Course extends BaseEntity implements Serializable,Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Integer cid; //课程编号
    private String cname; //课程名称
    private String catalogId; //版块ID
    private String introduce; //课程简介
    private String logo; //课程logo图片
    private int aid; //课程首页文章id;
    private String type; //课程类型
    private int browseNum; //浏览数量


}