package com.example.aml.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // #设置允许跨域请求的域名
                .allowCredentials(true) // #是否允许携带 Cookie
                .allowedMethods("*") // #设置允许的方法
                .maxAge(3600); // #跨域允许时间
    }
}

