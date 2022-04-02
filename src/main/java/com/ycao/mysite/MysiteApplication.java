package com.ycao.mysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching

public class MysiteApplication {

    public static void main(String[] args) {

        SpringApplication.run(MysiteApplication.class, args);
    }

}
