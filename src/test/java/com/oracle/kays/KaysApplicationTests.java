package com.oracle.kays;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.oracle.kays.controller.ArticleController;
import com.oracle.kays.entity.Article;
import com.oracle.kays.entity.Members;
import com.oracle.kays.service.ArticleService;
import com.oracle.kays.service.ChapterService;
import com.oracle.kays.service.MembersService;
import com.oracle.kays.util.MyUtils;
import com.oracle.kays.view.ChapterTreeMenu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

//@EnableSwagger2
@SpringBootTest
class KaysApplicationTests {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MembersService membersService;

    @Resource
    private ChapterService chapterService;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        System.out.println("成功");
    }
    @Test
    void test3(){

        Optional<List<ChapterTreeMenu>> optional = chapterService.initChapterTreeMenuByCidAndPid("9", -1);
        List<ChapterTreeMenu> ChapterTreeMenuList =optional.get();
                System.out.println(ChapterTreeMenuList);
    }
    @Test
    void test2(){
        Optional<Article> optional = articleService.queryArticleByAid(22);
        System.out.println(optional.isPresent());
        Optional<List<Members>> optiona2 = membersService.selectList(null);
        System.out.println(optiona2.isPresent());
    }

    @Test
    void test4(){
        MyUtils myUtils = new MyUtils();
        myUtils.sendMessageCodeByTenxunyun("17671312592");
//        Jedis jedis=new Jedis("127.0.0.1",6379 );
//        System.out.println(jedis.ping());
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
//        redisTemplate.opsForValue().set("mykey","0123");
//        System.out.println(redisTemplate.opsForValue().get("mykey"));
    }
    @Test
    void ceshi(){
        System.out.println(membersService.adminlogin("admin", "123456").get());

    }
}
