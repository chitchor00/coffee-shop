package org.swaroop.coffeestore.iservice;

import org.swaroop.coffeestore.dto.CartInfoDTO;
import org.swaroop.coffeestore.dto.DrinkDTO;
import org.swaroop.coffeestore.dto.ToppingDTO;
import org.swaroop.coffeestore.model.Drink;
import org.swaroop.coffeestore.model.Topping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IBeanMapper {
	DrinkDTO.DrinkRs toDrinkRs(Drink drink);

	Drink toDrink(DrinkDTO.DrinkRq drinkRq);

	void updateDrink(DrinkDTO.DrinkRq drinkRq, @MappingTarget Drink drink);

	ToppingDTO.ToppingRs toToppingRs(Topping topping);

	CartInfoDTO.DrinkInfoItem toDrinkItem(DrinkDTO.DrinkRs drinkRs);

	CartInfoDTO.ToppingInfoItem toToppingItem(ToppingDTO.ToppingRs toppingRs);
}
