package org.swaroop.coffeestore.repository;

import org.swaroop.coffeestore.dto.query.ToppingsByDrinksStatDTO;
import org.swaroop.coffeestore.model.Topping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IToppingRepository extends IBaseRepository<Topping, Integer> {
	@Query("""
		select new org.swaroop.coffeestore.dto.query.ToppingsByDrinksStatDTO(
			di.drink.name, di.drink.id, ti.topping.name, ti.topping.id, sum(di.count))
		from DrinkItem di
		join di.toppingItems ti
		group by di.drink.name, di.drink.id, ti.topping.name, ti.topping.id
		order by sum(di.count) desc""")
	List<ToppingsByDrinksStatDTO> getToppingsByDrinksStat();
}
