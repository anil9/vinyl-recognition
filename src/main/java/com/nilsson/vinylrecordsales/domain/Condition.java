package com.nilsson.vinylrecordsales.domain;

public enum Condition {
	NEW("new"), USED("used");
	private final String value;

	Condition(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}