package org.swaroop.coffeestore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Entity
@Table(name = "t_drink", uniqueConstraints = {
	@UniqueConstraint(name = "uc_drink", columnNames = "c_name")
})
@Getter
@Setter
@Accessors(chain = true)
public class Drink /*TODO extends Auditable*/ {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	@Column(name = "c_name", nullable = false)
	private String name;

	@NotNull
	@Positive
	@Column(name = "n_price", nullable = false)
	private BigDecimal price;
}
