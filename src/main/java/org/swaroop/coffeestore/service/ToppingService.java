package org.swaroop.coffeestore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.swaroop.coffeestore.CoffeeStoreException;
import org.swaroop.coffeestore.config.security.ETokenType;
import org.swaroop.coffeestore.dto.ToppingDTO;
import org.swaroop.coffeestore.dto.query.ToppingsByDrinksStatDTO;
import org.swaroop.coffeestore.repository.IToppingRepository;
import org.swaroop.coffeestore.iservice.IBeanMapper;
import org.swaroop.coffeestore.iservice.IToppingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToppingService implements IToppingService {
	private final IToppingRepository toppingRepository;
	private final IBeanMapper mapper;

	// ------------------------------

	@Override
	public List<ToppingDTO.ToppingRs> list() {
		return toppingRepository.findAll()
			.stream()
			.map(mapper::toToppingRs)
			.toList();
	}

	@Override
	public ToppingDTO.ToppingRs findById(Integer id) {
		return mapper.toToppingRs(toppingRepository.findById(id).orElseThrow(() -> new CoffeeStoreException("Topping Not Found: " + id)));
	}

	@PreAuthorize("hasAuthority('" + ETokenType.ADMIN + "')")
	@Override
	public List<ToppingsByDrinksStatDTO> getToppingsByDrinksStat() {
		return toppingRepository.getToppingsByDrinksStat();
	}
}
