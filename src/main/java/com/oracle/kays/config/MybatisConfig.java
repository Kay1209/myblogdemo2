package com.oracle.kays.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;

/*
* MybatisPlus的配置类
* */

@EnableTransactionManagement  //使乐观锁生效的一个注解
@Configuration

 class MybatisConfig {


    //分页插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusPagerInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    //基于mybatisplus 3.4.0 的写法
    @Bean
    public MybatisPlusInterceptor optimisticLockerInnerInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}


