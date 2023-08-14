package com.shazzad.auditdemo;

import com.shazzad.auditdemo.others.SecurityAuditorAware;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditDemoApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware(){
        return new SecurityAuditorAware();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
