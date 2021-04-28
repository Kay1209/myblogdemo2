package com.oracle.kays.entity;

import cn.hutool.extra.mail.MailUtil;
import com.oracle.kays.json.JsonResult;
import com.oracle.kays.service.MembersService;
import com.oracle.kays.service.impl.MembersServiceImpl;
import com.oracle.kays.util.MyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class MembersTest {

    @Autowired
    private MembersService membersService;


    @Test
    public void main1(){
        Members members = new Members();
        members.setUsername("阿诚");
        System.out.println(members);
    }

    @Test
    public void main2(){
        Members members = new Members();
        //members.setUid(13);
        members.setUsername("阿诚");
        members.setGender("男");
        members.setMobile("17671312592");
        members.setIsAdmin(0);
        members.setScore(0);
        members.setStatus(0);


        //System.out.println(members);
        System.out.println(membersService.insert(members));
    }

    @Test
    void main3(){

        Optional<List<Members>> optional = membersService.selectList(null);
        if(optional.isPresent()){
            System.out.println("成功");
            System.out.println(optional.get());
        }else
            System.out.println("失败");
    }

    @Test
    void yanzenma(){
        String identify="1209128805@qq.com";
        if (MyUtils.isPhoneLegal(identify)) {
            String randomCode = MyUtils.sendMessageCodeByTenxunyun(identify);
            //由于阿里云短信接口限制验证码暂时只能为1209
            System.out.println(("生成的验证码是：" + randomCode));
        } else if (MyUtils.isEmailLegal(identify)) {
            String randomCode = MyUtils.sendEmailValidateCode(identify);
            System.out.println(("生成的验证码是：" + randomCode));
        } else {
            System.out.println(("发送验证的账号格式不正确！"));
        }
    }
    @Test
    void ceshi(){
        MailUtil.send("1209128805@qq.com", "测试", "邮件来自Hutool测试", false);
    }
    @Test
    void ceshi2(){

    }
}
