package com.toy.blog.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.toy.blog.domain")
class DomainApplicationTests {

    @Test
    void contextLoads() {
    }

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("/Asia/Seoul"));
        System.out.println("현재 시각 : " + new Date());
    }

}
