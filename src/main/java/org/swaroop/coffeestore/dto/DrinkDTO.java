package org.swaroop.coffeestore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public abstract class DrinkDTO {
	@NotEmpty
	private String name;

	@NotNull
	@Positive
	private BigDecimal price;

	// ------------------------------

	public static class DrinkRq extends DrinkDTO {
	}

	@Getter
	@Setter
	@Accessors(chain = true)
	public static class DrinkRs extends DrinkDTO {
		public Integer id;
	}
}
