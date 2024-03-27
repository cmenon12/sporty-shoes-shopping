package com.sportyshoes.SportyShoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.sportyshoes")
@EntityScan(basePackages = "com.sportyshoes.entity")
@EnableJpaRepositories(basePackages = "com.sportyshoes.repository")
public class SportyShoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportyShoesApplication.class, args);
	}

}
