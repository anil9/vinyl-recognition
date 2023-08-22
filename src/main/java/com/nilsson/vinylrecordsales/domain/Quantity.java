package com.nilsson.vinylrecordsales.domain;

import static java.lang.String.format;

public record Quantity(Integer value) {
	public static final Quantity ONE = new Quantity(1);

	public Quantity {
		if (value == null || value < 0) {
			throw new IllegalArgumentException(format("Quantity %s cannot be less than 0", value));
		}
	}
}
