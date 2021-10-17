package com.nilsson.vinylrecordsales.domain;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class ShippingInformation {
	private final ShippingCompany shippingCompany;
	private final BigDecimal shippingPrice;
	private final PickupStrategy pickupStrategy;

	public ShippingInformation(ShippingCompany shippingCompany, BigDecimal shippingPrice, PickupStrategy pickupStrategy) {
		this.shippingCompany = requireNonNull(shippingCompany, "shippingCompany");
		this.shippingPrice = requireNonNull(shippingPrice, "shippingPrice");
		this.pickupStrategy = requireNonNull(pickupStrategy, "pickupStrategy");
	}

	public ShippingCompany getShippingCompany() {
		return shippingCompany;
	}

	public BigDecimal getShippingPrice() {
		return shippingPrice;
	}

	public PickupStrategy getPickupStrategy() {
		return pickupStrategy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ShippingInformation that = (ShippingInformation) o;
		return shippingCompany == that.shippingCompany && Objects.equals(shippingPrice, that.shippingPrice) && pickupStrategy == that.pickupStrategy;
	}

	@Override
	public int hashCode() {
		return Objects.hash(shippingCompany, shippingPrice, pickupStrategy);
	}

	@Override
	public String toString() {
		return "ShippingInformation{" +
				"shippingCompany=" + shippingCompany +
				", shippingPrice=" + shippingPrice +
				", pickupStrategy=" + pickupStrategy +
				'}';
	}
}
