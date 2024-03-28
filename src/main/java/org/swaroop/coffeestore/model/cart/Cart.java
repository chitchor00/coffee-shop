package org.swaroop.coffeestore.model.cart;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "t_cart")
@Getter
@Setter
@Accessors(chain = true)
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	@Column(name = "f_user", nullable = false)
	private String userId;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "f_cart", nullable = false, foreignKey = @ForeignKey(name = "fk_drinkitem2cart"))
	private List<DrinkItem> drinkItems;
}
