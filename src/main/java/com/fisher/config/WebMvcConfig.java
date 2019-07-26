package com.fisher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 跨域请求
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${web.cors.origins}")
    private String origins;
    @Value("${web.cors.credentials}")
    private boolean credentials;
    @Value("${web.cors.maxAge}")
    private Long maxAge;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowedHeaders("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                //设置是否允许跨域传cookie
                .allowCredentials(credentials);
//                .maxAge(maxAge)
    }

}
