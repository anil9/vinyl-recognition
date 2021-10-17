package com.nilsson.vinylrecordsales.domain;

public enum ShippingCompany {
	POSTEN,
	DHL,
	BUSSGODS,
	SCHENKER,
	OTHER;

	public String getValue(){
		return this.name().toLowerCase();
	}

}
