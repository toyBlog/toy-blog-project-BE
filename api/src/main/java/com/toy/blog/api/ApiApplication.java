package com.toy.blog.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@EntityScan(basePackages = {"com.toy.blog"})
@SpringBootApplication(scanBasePackages = "com.toy.blog")
public class ApiApplication {

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("/Asia/Seoul"));
        System.out.println("현재 시각 : " + new Date());
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
