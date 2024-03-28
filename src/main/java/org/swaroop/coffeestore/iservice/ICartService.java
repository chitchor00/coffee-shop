package org.swaroop.coffeestore.iservice;

import org.swaroop.coffeestore.dto.CartInfoDTO;
import org.swaroop.coffeestore.dto.CartRqDTO;

public interface ICartService {
	CartInfoDTO getCart();

	CartInfoDTO storeCart(CartRqDTO cartRq);

	void deleteCart();

	CartInfoDTO finalizeCart(CartRqDTO cartRq);
}
