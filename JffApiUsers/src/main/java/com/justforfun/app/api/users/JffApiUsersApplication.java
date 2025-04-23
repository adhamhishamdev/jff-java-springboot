package com.justforfun.app.api.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
// @EnableDiscoveryClient // no longer needed
@EnableFeignClients
public class JffApiUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(JffApiUsersApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@LoadBalanced // enable client side load balancing for the RestTemplate
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}

}
