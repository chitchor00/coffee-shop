package org.swaroop.coffeestore.iservice;

import org.swaroop.coffeestore.dto.ToppingDTO;
import org.swaroop.coffeestore.dto.query.ToppingsByDrinksStatDTO;

import java.util.List;

public interface IToppingService {
	List<ToppingDTO.ToppingRs> list();

	ToppingDTO.ToppingRs findById(Integer id);

	List<ToppingsByDrinksStatDTO> getToppingsByDrinksStat();
}
