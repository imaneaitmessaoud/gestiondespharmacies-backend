package com.pharmactrl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			String motDePasse = "123456";
			String hash = new BCryptPasswordEncoder().encode(motDePasse);
			System.out.println("Mot de passe encodé pour '123456' : " + hash);
		};
	}
}
