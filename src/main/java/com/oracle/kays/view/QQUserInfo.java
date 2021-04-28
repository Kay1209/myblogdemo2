package com.oracle.kays.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 会员QQ资料的视图
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QQUserInfo {
    private String openId; //等同于qq号码，用户的唯一标识符
    private String nickname; //QQ昵称
    private String userIconUrl; //QQ头像url
    private String gender; //QQ的性别
}
