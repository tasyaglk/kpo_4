package com.kpo.springshaurma;

import jwt.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.kpo.springshaurma.repository")
public class Application {
    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
