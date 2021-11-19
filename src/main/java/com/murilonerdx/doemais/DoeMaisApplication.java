package com.murilonerdx.doemais;

import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.repository.BusinessRepository;
import com.murilonerdx.doemais.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories
@EnableScheduling
public class DoeMaisApplication {

    @Autowired
    private BusinessRepository businessRepository;

    @Scheduled(cron = "0 0 0 1 1/1 *")
    public void agendamentoOrder() {
        List<Business> business = businessRepository.findAll();
        Set<Business> businessUpdate = business.stream().peek(x->{
            x.setTribute(0.00);
            x.setPoints(0.00);
        }).collect(Collectors.toSet());
        System.out.println(businessRepository.saveAll(businessUpdate));
    }

    public static void main(String[] args) {
        SpringApplication.run(DoeMaisApplication.class, args);
    }

}
