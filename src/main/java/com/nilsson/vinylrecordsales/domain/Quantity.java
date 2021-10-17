package com.nilsson.vinylrecordsales.domain;

import java.util.Objects;

import static java.lang.String.format;

public class Quantity {
	private final Integer value;
	public static final Quantity ONE = new Quantity(1);
	public static final Quantity ZERO = new Quantity(0);

	public Quantity(Integer value) {
		if (value == null || value < 0) {
			throw new IllegalArgumentException(format("Quantity %s cannot be less than 0", value));
		}
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Quantity quantity = (Quantity) o;
		return Objects.equals(value, quantity.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "Quantity{" +
				"value=" + value +
				'}';
	}
}
