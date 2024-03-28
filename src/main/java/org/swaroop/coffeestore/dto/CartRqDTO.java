package org.swaroop.coffeestore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CartRqDTO {
	@Valid
	@NotEmpty
	private List<DrinkRqItem> drinks;

	@Getter
	@Setter
	@Accessors(chain = true)
	public static class DrinkRqItem {
		@NotNull
		private Integer id;
		@Valid
		private List<ToppingRqItem> toppings;
		@NotNull
		@Min(1)
		private Integer count = 1;
	}

	@Getter
	@Setter
	public static class ToppingRqItem {
		@NotNull
		private Integer id;
	}

}
