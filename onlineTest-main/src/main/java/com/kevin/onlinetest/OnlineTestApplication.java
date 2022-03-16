package com.kevin.onlinetest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author herokilito
 */
@SpringBootApplication
@MapperScan("com.kevin.onlinetest.mapper")
public class OnlineTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineTestApplication.class, args);
    }

}
