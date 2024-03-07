package com.example.springyemian.config;

import com.example.springyemian.Interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns(
                "/**").excludePathPatterns("/index.html","/articles/users/**",
                "/error/**", "/css/**", "/imgs/**", "/js/**","/yzm/**"
                ,"/articles/userscode/**","/goods/class/**"
        );
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/yzm/**")
//                .addResourceLocations("file:C:/Users/Administrator/Desktop/yzm/");
                .addResourceLocations("file:/webpro/yzm/");
        registry.addResourceHandler("/pt/**")
//                .addResourceLocations("file:C:\\Users\\Administrator\\Desktop\\ce/");
                .addResourceLocations("file:/webpro/pt/");
    }
}
