package org.swaroop.coffeestore.dto.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ToppingsByDrinksStatDTO {
	private String coffee;
	private Integer coffeeId;
	private String topping;
	private Integer toppingId;
	private Long count;
}
