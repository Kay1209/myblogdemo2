package com.oracle.kays.entity;


//考试章节表

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="CHAPTER")
@JsonIgnoreProperties
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter extends BaseEntity implements Serializable,Cloneable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 章节编号

    private int pid; //父类章节编号
    private int level; //菜单的层数
    private int articleId; //文章编号
    private String cid; //课程编号
    private String cname;//章节名称
    private int count; //该章节的考试次数
    private String logo; //该章节的图片地址，图片名字。



}
