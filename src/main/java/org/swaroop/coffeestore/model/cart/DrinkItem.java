package org.swaroop.coffeestore.model.cart;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.swaroop.coffeestore.model.Drink;

import java.util.List;

@Entity
@Table(name = "t_cart_dink")
@Getter
@Setter
@Accessors(chain = true)
public class DrinkItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Min(1)
	@Column(name = "n_count", nullable = false)
	private Integer count;

	@ManyToOne
	@JoinColumn(name = "f_drink", nullable = false, insertable = false, updatable = false,
		foreignKey = @ForeignKey(name = "fk_drinkitem2drink"))
	private Drink drink;

	@Column(name = "f_drink", nullable = false)
	private Integer drinkId;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "f_drink_item", nullable = false, foreignKey = @ForeignKey(name = "fk_toppingitem2drinkitem"))
	private List<ToppingItem> toppingItems;
}
