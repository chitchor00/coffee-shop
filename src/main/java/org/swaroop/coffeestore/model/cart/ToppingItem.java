package org.swaroop.coffeestore.model.cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.swaroop.coffeestore.model.Topping;

@Entity
@Table(name = "t_cart_dink_topping")
@Getter
@Setter
@Accessors(chain = true)
public class ToppingItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "f_topping", nullable = false, insertable = false, updatable = false,
		foreignKey = @ForeignKey(name = "fk_cart2topping"))
	private Topping topping;

	@Column(name = "f_topping", nullable = false)
	private Integer toppingId;
}
