package org.zero.bms.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.zero.bms.service", "org.zero.bms.web"})
@EntityScan(basePackages = "org.zero.bms.entity.po")
@EnableJpaRepositories(basePackages = "org.zero.bms.dao")
public class BmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BmsApplication.class, args);
    }
}
