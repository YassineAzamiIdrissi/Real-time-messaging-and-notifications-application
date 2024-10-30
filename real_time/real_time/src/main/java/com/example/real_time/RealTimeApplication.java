package com.example.real_time;

import com.example.real_time.Authority.Authority;
import com.example.real_time.Authority.AuthorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class RealTimeApplication {
    @Bean
    public CommandLineRunner commandLineRunner(AuthorityRepository authRepo) {
        return args -> {
            if (authRepo.findByAuthority("USER").isEmpty()) {
                authRepo.save(Authority.builder().authority("USER").build());
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(RealTimeApplication.class, args);
    }

}
