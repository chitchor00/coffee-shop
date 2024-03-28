package org.swaroop.coffeestore.discount;

import java.util.List;

/**
 * This abstract class defines a composite pattern for calculating a discount
 * using returned values from its {@link #children} strategies.
 */
public abstract class ACompositeDiscountStrategy implements IDiscountStrategy {
	protected final List<IDiscountStrategy> children;

	public ACompositeDiscountStrategy(List<IDiscountStrategy> children) {
		if (children == null || children.isEmpty()) {
			throw new RuntimeException("Invalid number of children");
		}
		this.children = children;
	}
}
