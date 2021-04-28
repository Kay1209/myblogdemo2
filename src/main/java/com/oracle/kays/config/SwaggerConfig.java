package com.oracle.kays.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/*
* swagger接口测试配置类
*
* */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket applicationApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(applicationInfo())
                .select()
                //swagger要扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.oracle.kays.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo applicationInfo() {
        return  new ApiInfoBuilder()
                .title("你的网站标题")
                .description("你的网站描述")
                .termsOfServiceUrl("localhost:8181/myblog")
                .contact(new Contact("Swagger测试","localhost:8181/myblog/swagger-ui.html","1209128805@qq.com"))
                .version("1.0")
                .build();
    }
}
