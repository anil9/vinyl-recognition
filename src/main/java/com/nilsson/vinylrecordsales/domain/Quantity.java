package com.nilsson.vinylrecordsales.domain;

import static java.lang.String.format;

public class Quantity {
	private final Integer value;

	public Quantity(Integer value) {
		if(value == null || value < 0){
			throw new IllegalArgumentException(format("Quantity %s cannot be less than 0", value));
		}
		this.value = value;
	}
}
