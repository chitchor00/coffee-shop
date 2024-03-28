package org.swaroop.coffeestore.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class CoffeeStoreProperties {
	private String adminToken = "admin";
	private Discount discount = new Discount();

	@Getter
	@Setter
	public static class Discount {
		private AmountLimitDiscount amountLimit = new AmountLimitDiscount();
		private CountLimitDiscount countLimit = new CountLimitDiscount();
	}

	@Getter
	@Setter
	public static class AmountLimitDiscount {
		private BigDecimal floor = new BigDecimal(12);
		private BigDecimal percent = new BigDecimal("0.25");
	}

	@Getter
	@Setter
	public static class CountLimitDiscount {
		private Integer count = 3;
	}
}
