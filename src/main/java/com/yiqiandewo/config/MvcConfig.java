package com.yiqiandewo.config;

import com.yiqiandewo.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 如果您希望保留Spring Boot MVC功能，并且希望添加其他MVC配置（拦截器、格式化程序、视图控制器和其他功能），则可以添加自己
 * 的@configuration类，类型为WebMvcConfigurer，但不添加@EnableWebMvc。如果希望提供
 * RequestMappingHandlerMapping、RequestMappingHandlerAdapter或ExceptionHandlerExceptionResolver的自定义
 * 实例，则可以声明WebMvcRegistrationsAdapter实例来提供此类组件。
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/userLogin").setViewName("login");
        registry.addViewController("/main").setViewName("dashboard");
        registry.addViewController("/list").setViewName("emp/list");
        registry.addViewController("/add").setViewName("emp/add");
        registry.addViewController("/update").setViewName("emp/update");
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

}
