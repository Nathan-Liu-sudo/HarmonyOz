package com.tp11.harmonyoz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tp11.harmonyoz.mapper")
public class HarmonyOzApplication {

    public static void main(String[] args) {
        SpringApplication.run(HarmonyOzApplication.class, args);
    }

}
