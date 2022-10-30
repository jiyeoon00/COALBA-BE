package com.project.coalba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CoalbaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoalbaApplication.class, args);
	}
}
