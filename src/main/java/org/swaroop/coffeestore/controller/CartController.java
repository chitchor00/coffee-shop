package org.swaroop.coffeestore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.swaroop.coffeestore.IConst;
import org.swaroop.coffeestore.dto.CartInfoDTO;
import org.swaroop.coffeestore.dto.CartRqDTO;
import org.swaroop.coffeestore.iservice.ICartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(IConst.API_URI_PREFIX)
@RequiredArgsConstructor
public class CartController {
	private final ICartService cartService;

	// ------------------------------

	@GetMapping("/ordering/cart")
	public CartInfoDTO getCart() {
		return cartService.getCart();
	}

	@PostMapping("/ordering/cart")
	public CartInfoDTO storeCart(@Valid @RequestBody CartRqDTO cartRq) {
		return cartService.storeCart(cartRq);
	}

	@PostMapping("/ordering/purchases")
	public CartInfoDTO finalizeCart(@Valid @RequestBody CartRqDTO cartRq) {
		return cartService.finalizeCart(cartRq);
	}
}
