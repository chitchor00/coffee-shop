package org.swaroop.coffeestore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.swaroop.coffeestore.CoffeeStoreException;
import org.swaroop.coffeestore.config.security.ETokenType;
import org.swaroop.coffeestore.dto.DrinkDTO;
import org.swaroop.coffeestore.iservice.IDrinkService;
import org.swaroop.coffeestore.model.Drink;
import org.swaroop.coffeestore.repository.IDrinkRepository;
import org.swaroop.coffeestore.iservice.IBeanMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DrinkService implements IDrinkService {
	private final IDrinkRepository drinkRepository;
	private final IBeanMapper mapper;

	// ------------------------------

	@Override
	public List<DrinkDTO.DrinkRs> list() {
		return drinkRepository.findAll()
			.stream()
			.map(mapper::toDrinkRs)
			.toList();
	}

	@Override
	public DrinkDTO.DrinkRs findById(Integer id) {
		return mapper.toDrinkRs(drinkRepository.findById(id).orElseThrow(() -> new CoffeeStoreException("Drink Not Found: " + id)));
	}

	@PreAuthorize("hasAuthority('" + ETokenType.ADMIN + "')")
	@Transactional
	@Override
	public DrinkDTO.DrinkRs create(DrinkDTO.DrinkRq drinkRq) {
		final Drink drink = mapper.toDrink(drinkRq);
		final Drink savedDrink = drinkRepository.saveAndFlush(drink);
		return mapper.toDrinkRs(savedDrink);
	}

	@PreAuthorize("hasAuthority('" + ETokenType.ADMIN + "')")
	@Transactional
	@Override
	public DrinkDTO.DrinkRs update(Integer id, DrinkDTO.DrinkRq drinkRq) {
		final Drink loadedDrink = drinkRepository.findById(id).orElseThrow(() -> new CoffeeStoreException("Drink Not Found: " + id));
		mapper.updateDrink(drinkRq, loadedDrink);
		final Drink updatedDrink = drinkRepository.saveAndFlush(loadedDrink);
		return mapper.toDrinkRs(updatedDrink);
	}
}
