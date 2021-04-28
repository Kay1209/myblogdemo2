package com.oracle.kays.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

//会员表
@Entity
@Table(name="MEMBERS")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Members extends BaseEntity implements Serializable,Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Integer uid; //编号
    private String username; //用户名，昵称
    private String email; //电子邮箱用户名
    private String mobile; //手机号码
    private String password; //密码
    private String birthday; //出生日期
    private String gender = "男"; //性别
    private int status; //用户状态
    private String openId; //实现第三方登录的openId
    private String nickname; //QQ的昵称
    private String userIconUrl; //QQ头像的地址
    private String province; //省份
    private String city; //城市
    private String zone;  //区，县
    private String address; //街道，地址
    private String idCard; //身份证号码
    private String regType; //注册类型(0:手机注册，1：电子邮箱注册，2：第三方登录)
    private String type = "0"; //用户类型(0:学生 1:教师  2:家长)
    private String school; //学生或者老师、家长所在学校名字
    private int score; //积分
    private String recommendation; //推荐码
    private String recommendId; //会员自己的推荐码
    private int isAdmin = 0; //是否是超级管理员
    private String token; //生成的token

    @Transient //jpa的语法规则，表示validateCode不会映射为表字段
    @TableField(exist = false) //mybatis plus语法，表示validateCode不会映射为表字段
    private String validateCode;
}
