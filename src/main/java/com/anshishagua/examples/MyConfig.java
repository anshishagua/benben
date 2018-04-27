package com.anshishagua.examples;

import com.anshishagua.annotations.Bean;
import com.anshishagua.annotations.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午2:43
 */

@Configuration
public class MyConfig {
    @Bean
    public LocalDate today() {
        return LocalDate.now();
    }

    @Bean
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @Bean
    public String name() {
        return "bbbbbb";
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }
}