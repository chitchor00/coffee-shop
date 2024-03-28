package org.swaroop.coffeestore.discount;

import org.swaroop.coffeestore.dto.CartInfoDTO;

import java.math.BigDecimal;

/**
 * All discount strategies should implement this interface.
 * The {@link #calculateDiscount} method returns the amount of
 * discount for the passed {@code CartInfoDTO},
 * otherwise {@code BigDecimal.ZERO} if {@code cart} is not eligible for discount.
 */
public interface IDiscountStrategy {

	/**
	 * Calculate the discount and return it.
	 *
	 * @param cart The {@linkplain CartInfoDTO} has all the data for the cart
	 * @return the amount of discount, otherwise {@code BigDecimal.ZERO}
	 */
	BigDecimal calculateDiscount(CartInfoDTO cart);
}
