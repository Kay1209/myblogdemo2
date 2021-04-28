package com.oracle.kays.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyConfig {

    private static String accessKeyId ;
    private static String accessKeySecret ;

    @Value("${Tencentyunconfig.accesskeyid}")
    public  void setAccessKeyId(String accessKeyId) {
        MyConfig.accessKeyId = accessKeyId;
    }

    @Value("${Tencentyunconfig.accesskeysecret}")
    public  void setAccessKeySecret(String accessKeySecret) {
        MyConfig.accessKeySecret = accessKeySecret;
    }

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }
}
