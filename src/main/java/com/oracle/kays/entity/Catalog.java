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
@Table(name="CATALOG")
@JsonIgnoreProperties
@Data
@NoArgsConstructor
@AllArgsConstructor

//版块表
public class Catalog extends BaseEntity implements Serializable,Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Integer cid;
    @Column(length = 50)
    private String id; //必须字符串，类别编号
    @Column(length = 50)
    private String cname; //类名名称
    @Column(length = 50)
    private String pid; //父类的编号


}
