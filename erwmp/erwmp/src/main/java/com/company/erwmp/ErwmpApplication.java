package com.company.erwmp;

import com.company.erwmp.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching   //turns the caching system on
@EnableJpaAuditing(auditorAwareRef = "auditorAware") //Track create/update events automatically
public class ErwmpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErwmpApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			userService.createUser(
//					"test",
//					"test@company.com",
//					"hs2326"
//			);
//		};
//	}
}
