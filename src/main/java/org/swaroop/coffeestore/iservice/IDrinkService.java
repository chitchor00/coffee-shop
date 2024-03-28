package org.swaroop.coffeestore.iservice;

import org.swaroop.coffeestore.dto.DrinkDTO;

import java.util.List;

public interface IDrinkService {
	List<DrinkDTO.DrinkRs> list();

	DrinkDTO.DrinkRs findById(Integer id);

	DrinkDTO.DrinkRs create(DrinkDTO.DrinkRq drinkRq);

	DrinkDTO.DrinkRs update(Integer id, DrinkDTO.DrinkRq drinkRq);
}
