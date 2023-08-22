package com.nilsson.vinylrecordsales.domain;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

public record ShippingInformation(ShippingCompany shippingCompany, BigDecimal shippingPrice,
								  PickupStrategy pickupStrategy) {
	public ShippingInformation(ShippingCompany shippingCompany, BigDecimal shippingPrice, PickupStrategy pickupStrategy) {
		this.shippingCompany = requireNonNull(shippingCompany, "shippingCompany");
		this.shippingPrice = requireNonNull(shippingPrice, "shippingPrice");
		this.pickupStrategy = requireNonNull(pickupStrategy, "pickupStrategy");
	}
}
