package org.swaroop.coffeestore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.swaroop.coffeestore.IConst;
import org.swaroop.coffeestore.dto.DrinkDTO;
import org.swaroop.coffeestore.dto.ToppingDTO;
import org.swaroop.coffeestore.dto.query.ToppingsByDrinksStatDTO;
import org.swaroop.coffeestore.iservice.IDrinkService;
import org.swaroop.coffeestore.iservice.IToppingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(IConst.API_URI_PREFIX)
@RequiredArgsConstructor
public class InventoryController {
	private final IDrinkService drinkService;
	private final IToppingService toppingService;

	// ------------------------------

	@GetMapping("/drinks")
	public List<DrinkDTO.DrinkRs> listDrinks() {
		return drinkService.list();
	}

	@PostMapping("/drinks")
	public DrinkDTO.DrinkRs saveDrink(@Valid @RequestBody DrinkDTO.DrinkRq drinkRq) {
		return drinkService.create(drinkRq);
	}

	@PutMapping("/drinks/{id}")
	public DrinkDTO.DrinkRs updateDrink(@PathVariable Integer id, @Valid @RequestBody DrinkDTO.DrinkRq drinkRq) {
		return drinkService.update(id, drinkRq);
	}

	// --------------- TOPPING

	@GetMapping("/toppings")
	public List<ToppingDTO.ToppingRs> listToppings() {
		return toppingService.list();
	}

	// TODO: @PostMapping("/toppings") to create a topping
	// TODO: @PutMapping("/toppings") to update a topping

	@GetMapping("/toppings/stats")
	public List<ToppingsByDrinksStatDTO> getToppingsByDrinksStat() {
		return toppingService.getToppingsByDrinksStat();
	}
}
