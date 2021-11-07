package com.murilonerdx.doemais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories
@EnableAutoConfiguration
public class DoeMaisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoeMaisApplication.class, args);
    }

}
