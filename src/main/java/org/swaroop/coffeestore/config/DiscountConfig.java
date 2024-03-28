package org.swaroop.coffeestore.config;

import lombok.RequiredArgsConstructor;
import org.swaroop.coffeestore.discount.AmountLimitDiscountStrategy;
import org.swaroop.coffeestore.discount.CountLimitDiscountStrategy;
import org.swaroop.coffeestore.discount.HighestDiscountStrategy;
import org.swaroop.coffeestore.discount.IDiscountStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DiscountConfig {
	private final CoffeeStoreProperties properties;

	@Bean
	public IDiscountStrategy discountStrategy() {
		return new HighestDiscountStrategy(List.of(
			new AmountLimitDiscountStrategy(
				properties.getDiscount().getAmountLimit().getFloor(),
				properties.getDiscount().getAmountLimit().getPercent()),
			new CountLimitDiscountStrategy(properties.getDiscount().getCountLimit().getCount())
		));
	}
}
