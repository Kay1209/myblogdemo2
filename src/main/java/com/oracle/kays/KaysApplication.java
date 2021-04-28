package com.oracle.kays;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.SpringVersion;

@SpringBootApplication
@MapperScan("com.oracle.kays.mapper")
public class KaysApplication {

    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        new SpringApplicationBuilder(KaysApplication.class)//
                .main(SpringVersion.class) // 这个是为了可以加载 Spring 版本
                .bannerMode(Banner.Mode.CONSOLE)// 控制台打印
                .run(args);
    }

}
