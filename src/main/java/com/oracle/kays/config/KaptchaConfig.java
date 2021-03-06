package com.oracle.kays.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/*
* kaptcha.border	图片边框，合法值：yes , no	yes
kaptcha.border.color	边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.	black
kaptcha.border.thickness	边框厚度，合法值：>0	1
kaptcha.image.width	图片宽	200
kaptcha.image.height	图片高	50
kaptcha.producer.impl	图片实现类	com.google.code.kaptcha.impl.DefaultKaptcha
kaptcha.textproducer.impl	文本实现类	com.google.code.kaptcha.text.impl.DefaultTextCreator
kaptcha.textproducer.char.string	文本集合，验证码值从此集合中获取	abcde2345678gfynmnpwx
kaptcha.textproducer.char.length	验证码长度	5
kaptcha.textproducer.font.names	字体	Arial, Courier
kaptcha.textproducer.font.size	字体大小	40px.
kaptcha.textproducer.font.color	字体颜色，合法值： r,g,b  或者 white,black,blue.	black
kaptcha.textproducer.char.space	文字间隔	2
kaptcha.noise.impl	干扰实现类	com.google.code.kaptcha.impl.DefaultNoise
kaptcha.noise.color	干扰颜色，合法值： r,g,b 或者 white,black,blue.	black
kaptcha.obscurificator.impl	图片样式：
水纹com.google.code.kaptcha.impl.WaterRipple
鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
阴影com.google.code.kaptcha.impl.ShadowGimpy	com.google.code.kaptcha.impl.WaterRipple
kaptcha.background.impl	背景实现类	com.google.code.kaptcha.impl.DefaultBackground
kaptcha.background.clear.from	背景颜色渐变，开始颜色	light grey
kaptcha.background.clear.to	背景颜色渐变，结束颜色	white
kaptcha.word.impl	文字渲染器	com.google.code.kaptcha.text.impl.DefaultWordRenderer
kaptcha.session.key	session key	KAPTCHA_SESSION_KEY
kaptcha.session.date	session date	KAPTCHA_SESSION_DATE
---------------------
作者：Remember_Ray
来源：CSDN
原文：https://blog.csdn.net/q343509740/article/details/80732802
版权声明：本文为博主原创文章，转载请附上博文链接！
*
*
* */

@Component
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "110");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        properties.setProperty("kaptcha.session.key", "code");
        /*
        * 图片样式：
            水纹com.google.code.kaptcha.impl.WaterRipple
            鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
            阴影com.google.code.kaptcha.impl.ShadowGimpy
        * */

        //properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        //properties.setProperty("kaptcha.obscurificator.impl", null);
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "Arial,Courier");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        //properties.setProperty("kaptcha.word.impl", "com.google.code.kaptcha.text.impl.DefaultWordRenderer");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }

}
