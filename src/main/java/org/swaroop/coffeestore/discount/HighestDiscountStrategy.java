package org.swaroop.coffeestore.discount;

import lombok.extern.slf4j.Slf4j;
import org.swaroop.coffeestore.dto.CartInfoDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * This concrete composite strategy finds the highest discount among its {@link #children}
 */
@Slf4j
public class HighestDiscountStrategy extends ACompositeDiscountStrategy {
	public HighestDiscountStrategy(List<IDiscountStrategy> children) {
		super(children);
	}

	// ------------------------------

	@Override
	public BigDecimal calculateDiscount(CartInfoDTO cart) {
		final BigDecimal discount = children.stream()
			.map(strategy -> strategy.calculateDiscount(cart))
			.max(BigDecimal::compareTo)
			.orElse(BigDecimal.ZERO);

		log.info("HighestDiscountStrategy: id=[{}] value=[{}]", cart.getUserId(), discount);

		return discount;
	}
}
