package com.nilsson.vinylrecordsales.domain;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

public class ShippingInformation {
	private final ShippingCompany shippingCompany;
	private final BigDecimal shippingPrice;
	private final Boolean allowPickup;

	public ShippingInformation(ShippingCompany shippingCompany, BigDecimal shippingPrice, Boolean allowPickup) {
		this.shippingCompany = requireNonNull(shippingCompany, "shippingCompany");
		this.shippingPrice = requireNonNull(shippingPrice, "shippingPrice");
		this.allowPickup = requireNonNull(allowPickup, "allowPickup");
	}
}
