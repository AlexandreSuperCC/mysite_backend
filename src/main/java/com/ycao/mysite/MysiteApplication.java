package com.ycao.mysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class MysiteApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(MysiteApplication.class, args);
    }

}
