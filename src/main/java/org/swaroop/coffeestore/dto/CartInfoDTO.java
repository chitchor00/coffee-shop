package org.swaroop.coffeestore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartInfoDTO {
	private final String userId;
	private List<DrinkInfoItem> drinks;
	private BigDecimal discount = BigDecimal.ZERO;

	private Integer factorId;

	private Instant createTime;
	private Instant lastModifiedTime;

	// ---------------

	public BigDecimal getTotalAmount() {
		return drinks != null ?
			drinks.stream()
				.map(DrinkInfoItem::getTotalAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add) :
			BigDecimal.ZERO;
	}

	public Integer getNoOfDrinks() {
		return drinks != null ?
			drinks.stream()
				.map(DrinkInfoItem::getCount)
				.reduce(0, Integer::sum) :
			0;
	}

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	public static class DrinkInfoItem {
		private Integer id;
		private String name;
		private BigDecimal price;
		private List<ToppingInfoItem> toppings;
		private Integer count = 1;

		public BigDecimal getTotalAmount() {
			return getTotalPriceOfSingleItem().multiply(new BigDecimal(count));
		}

		@JsonIgnore
		public BigDecimal getTotalPriceOfSingleItem() {
			final BigDecimal sumOfToppings = toppings != null ?
				toppings.stream()
					.map(ToppingInfoItem::getPrice)
					.reduce(BigDecimal.ZERO, BigDecimal::add) :
				BigDecimal.ZERO;

			return price.add(sumOfToppings);

		}
	}

	@Getter
	@Setter
	@Accessors(chain = true)
	public static class ToppingInfoItem {
		private Integer id;
		private String name;
		private BigDecimal price;
	}
}
