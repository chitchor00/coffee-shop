package org.swaroop.coffeestore.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.swaroop.coffeestore.CoffeeStoreException;
import org.swaroop.coffeestore.dto.CartInfoDTO;
import org.swaroop.coffeestore.dto.CartRqDTO;
import org.swaroop.coffeestore.iservice.ICartService;
import org.swaroop.coffeestore.iservice.IDrinkService;
import org.swaroop.coffeestore.model.cart.Cart;
import org.swaroop.coffeestore.model.cart.DrinkItem;
import org.swaroop.coffeestore.model.cart.ToppingItem;
import org.swaroop.coffeestore.repository.ICartRepository;
import org.swaroop.coffeestore.discount.IDiscountStrategy;
import org.swaroop.coffeestore.iservice.IBeanMapper;
import org.swaroop.coffeestore.iservice.IToppingService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService implements ICartService {
	//TODO: store this map in Redis with expiration!
	private final Map<String, CartInfoDTO> CUSTOMERS_CARTS = new ConcurrentHashMap<>();

	private final IBeanMapper beanMapper;
	private final IDrinkService drinkService;
	private final IToppingService toppingService;
	private final ICartRepository cartRepository;
	private final IDiscountStrategy discountStrategy;

	// ------------------------------

	@Override
	public CartInfoDTO getCart() {
		assertCartAvailable();

		return getOrCreateCartForCurrentUser();
	}

	@Override
	public CartInfoDTO storeCart(@Valid CartRqDTO cartRq) {
		final CartInfoDTO cartInfoDTO = manageCart(cartRq);

		final Instant now = Instant.now();
		if (cartInfoDTO.getCreateTime() == null) {
			cartInfoDTO.setCreateTime(now);
		} else {
			cartInfoDTO.setLastModifiedTime(now);
		}

		return cartInfoDTO;
	}

	@Override
	public void deleteCart() {
		CUSTOMERS_CARTS.remove(getCurrentUser());
	}

	@Override
	public CartInfoDTO finalizeCart(@Valid CartRqDTO cartRq) {
		final CartInfoDTO cartInfoDTO = storeCart(cartRq);

		final Cart cart = new Cart().setUserId(cartInfoDTO.getUserId());
		cart.setDrinkItems(cartInfoDTO.getDrinks().stream().map(drink -> {
				final DrinkItem drinkItem = new DrinkItem()
					.setDrinkId(drink.getId())
					.setCount(drink.getCount());

				if (drink.getToppings() != null) {
					drinkItem.setToppingItems(drink.getToppings().stream()
						.map(topping -> new ToppingItem().setToppingId(topping.getId()))
						.collect(Collectors.toList()));
				}

				return drinkItem;
			})
			.collect(Collectors.toList()));

		final Cart savedCart = cartRepository.saveAndFlush(cart);
		cartInfoDTO.setFactorId(savedCart.getId());

		CUSTOMERS_CARTS.remove(getCurrentUser());

		return cartInfoDTO;
	}

	// ------------------------------

	private String getCurrentUser() {
		return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private void assertCartAvailable() {
		if (!CUSTOMERS_CARTS.containsKey(getCurrentUser())) {
			throw new CoffeeStoreException("Cart Not Found");
		}
	}

	private CartInfoDTO getOrCreateCartForCurrentUser() {
		return CUSTOMERS_CARTS.computeIfAbsent(getCurrentUser(), CartInfoDTO::new);
	}

	private CartInfoDTO manageCart(CartRqDTO cartRq) {
		final CartInfoDTO cartInfoDTO = getOrCreateCartForCurrentUser();

		cartInfoDTO.setDrinks(cartRq.getDrinks().stream().map(drinkRq -> {
				final CartInfoDTO.DrinkInfoItem drinkInfoItem = beanMapper.toDrinkItem(drinkService.findById(drinkRq.getId()));

				drinkInfoItem.setCount(drinkRq.getCount());

				if (drinkRq.getToppings() != null) {
					drinkInfoItem.setToppings(drinkRq.getToppings().stream()
						.map(toppingRq -> beanMapper.toToppingItem(toppingService.findById(toppingRq.getId())))
						.collect(Collectors.toList()));
				}

				return drinkInfoItem;
			})
			.collect(Collectors.toList()));

		cartInfoDTO.setDiscount(discountStrategy.calculateDiscount(cartInfoDTO));

		return cartInfoDTO;
	}
}
