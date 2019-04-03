package com.xzz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.xzz.config")
@MapperScan(basePackages = "com.xzz.mapper")
public class SpringdemoRzApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringdemoRzApplication.class, args);
    }

}
