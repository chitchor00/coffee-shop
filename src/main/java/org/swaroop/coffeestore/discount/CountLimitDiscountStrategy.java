package org.swaroop.coffeestore.discount;

import lombok.extern.slf4j.Slf4j;
import org.swaroop.coffeestore.dto.CartInfoDTO;

import java.math.BigDecimal;

/**
 * An implementation as a concrete strategy for {@code IDiscountStrategy}.
 *
 * <p>If the {@link CartInfoDTO#getNoOfDrinks() number of drinks in the cart} is equal or more
 * than {@linkplain #countLimit count limit}, this strategy finds a drink (including its toppings) with
 * the lowest price, and returns that as the discount.
 */
@Slf4j
public class CountLimitDiscountStrategy implements IDiscountStrategy {

	/**
	 * The required minimum number of drinks in the cart. This field should have value and greater that zero.
	 */
	private final Integer countLimit;

	// ------------------------------

	public CountLimitDiscountStrategy(Integer countLimit) {
		if (countLimit == null || countLimit <= 0) {
			throw new RuntimeException("Invalid count limit: " + countLimit);
		}
		this.countLimit = countLimit;
	}

	// ------------------------------

	@Override
	public BigDecimal calculateDiscount(CartInfoDTO cart) {
		final BigDecimal discount = cart.getNoOfDrinks() >= countLimit ?
			cart.getDrinks().stream()
				.map(CartInfoDTO.DrinkInfoItem::getTotalPriceOfSingleItem)
				.min(BigDecimal::compareTo)
				.orElse(BigDecimal.ZERO) :
			BigDecimal.ZERO;

		log.info("CountLimitDiscountStrategy: id=[{}] value=[{}]", cart.getUserId(), discount);

		return discount;
	}
}
