package org.swaroop.coffeestore.repository;

import org.swaroop.coffeestore.model.Drink;
import org.springframework.stereotype.Repository;

@Repository
public interface IDrinkRepository extends IBaseRepository<Drink, Integer> {
}
