package org.swaroop.coffeestore.repository;

import org.swaroop.coffeestore.model.cart.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository extends IBaseRepository<Cart, Integer> {
}
