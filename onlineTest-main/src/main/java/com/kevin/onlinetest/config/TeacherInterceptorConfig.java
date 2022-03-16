package com.kevin.onlinetest.config;

import com.kevin.onlinetest.interceptor.AdminAuthInterceptor;
import com.kevin.onlinetest.interceptor.TeacherAuthInterceptor;
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
 * @date 2020/12/31 21:06
 */
@Configuration
public class TeacherInterceptorConfig implements WebMvcConfigurer {

    @Bean
    public TeacherAuthInterceptor teacherAuthInterceptor() {
        return new TeacherAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludes = new ArrayList<>();
        excludes.add("/teacher/login");
        excludes.add("/teacher/isLogin");

        registry.addInterceptor(teacherAuthInterceptor())
                .addPathPatterns("/teacher/*")
                .excludePathPatterns(excludes);
    }
}
