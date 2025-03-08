package com.py.config;

import com.py.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\upload\\";
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+path);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns(
//                "/user/login",
//                "/user/register",
//                "/user/logout",
//                "/error",
//                "/favicon.ico"
//        );
//    }

//    @Bean
//    public LoginInterceptor loginInterceptor() {
//        return new LoginInterceptor();
//    }

}