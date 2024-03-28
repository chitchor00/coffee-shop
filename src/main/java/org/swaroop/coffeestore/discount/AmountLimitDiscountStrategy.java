package org.swaroop.coffeestore.discount;

import lombok.extern.slf4j.Slf4j;
import org.swaroop.coffeestore.dto.CartInfoDTO;

import java.math.BigDecimal;

/**
 * An implementation as a concrete strategy for {@code IDiscountStrategy}.
 *
 * <p>If the {@link CartInfoDTO#getTotalAmount() cart total amount}
 * is greater than or equal to the {@linkplain #floorLimit floor limit}, then the discount is calculated
 * using {@linkplain #discountPercent percent} of total amount.
 */
@Slf4j
public class AmountLimitDiscountStrategy implements IDiscountStrategy {
	/**
	 * The required minimum total amount in the cart for discount.
	 * This field should have value, and it must be greater that {@link BigDecimal#ZERO}.
	 */
	private final BigDecimal floorLimit;

	/**
	 * The percent value of total amount for discount.
	 * This field should have value, and it must be greater than {@link BigDecimal#ZERO}
	 * and less than {@link BigDecimal#ONE}.
	 */
	private final BigDecimal discountPercent;

	// ------------------------------

	public AmountLimitDiscountStrategy(BigDecimal floorLimit, BigDecimal discountPercent) {
		if (floorLimit == null || floorLimit.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Invalid floor limit: " + floorLimit);
		}

		if (discountPercent == null || discountPercent.compareTo(BigDecimal.ZERO) <= 0 ||
			discountPercent.compareTo(BigDecimal.ONE) >= 0) {
			throw new RuntimeException("Invalid percent for discount: " + discountPercent);
		}

		this.floorLimit = floorLimit;
		this.discountPercent = discountPercent;
	}

	// ------------------------------

	@Override
	public BigDecimal calculateDiscount(CartInfoDTO cart) {
		final BigDecimal discount = cart.getTotalAmount().compareTo(floorLimit) >= 0 ?
			cart.getTotalAmount().multiply(discountPercent) :
			BigDecimal.ZERO;

		log.info("AmountLimitDiscountStrategy: id=[{}] value=[{}]", cart.getUserId(), discount);

		return discount;
	}
}
