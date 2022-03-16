package com.kevin.onlinetest;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.script.ScriptUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

@SpringBootTest
class OnlineTestApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testVerify() {
        List<String> res = RuntimeUtil.execForLines("java", "-version");
        res.forEach(System.out::println);
        System.out.println(Validator.isBirthday("2020.1.1"));
        System.out.println(HttpStatus.OK.value());
        System.out.println(cn.hutool.http.HttpStatus.HTTP_OK);
    }
}
