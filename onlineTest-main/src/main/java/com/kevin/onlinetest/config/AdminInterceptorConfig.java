package com.kevin.onlinetest.config;

import com.kevin.onlinetest.interceptor.AdminAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author herokilito
 */
@Configuration
public class AdminInterceptorConfig implements WebMvcConfigurer {

    @Bean
    public AdminAuthInterceptor adminAuthInterceptor() {
        return new AdminAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludes = new ArrayList<>();
        excludes.add("/admin/login");
        excludes.add("/admin/isLogin");

        registry.addInterceptor(adminAuthInterceptor())
                .addPathPatterns("/admin/*")
                .excludePathPatterns(excludes);
    }
}
