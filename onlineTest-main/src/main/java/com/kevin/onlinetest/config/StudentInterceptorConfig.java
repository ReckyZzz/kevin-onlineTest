package com.kevin.onlinetest.config;

import com.kevin.onlinetest.interceptor.StudentAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.config
 * @date 2021/1/3 16:06
 */
@Configuration
public class StudentInterceptorConfig implements WebMvcConfigurer {

    @Bean
    StudentAuthInterceptor studentAuthInterceptor() {
        return new StudentAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludes = new ArrayList<>();
        excludes.add("/student/login");
        excludes.add("/student/isLogin");

        registry.addInterceptor(studentAuthInterceptor())
                .addPathPatterns("/student/*")
                .excludePathPatterns(excludes);
    }
}
