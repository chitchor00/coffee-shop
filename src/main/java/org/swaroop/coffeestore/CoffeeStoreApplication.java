package org.swaroop.coffeestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class CoffeeStoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(CoffeeStoreApplication.class, args);
	}
}
