package com.nilsson.vinylrecordsales.domain;

import static java.util.Objects.requireNonNull;

public record ProductId(Integer id) {
	public ProductId(Integer id) {
		this.id = requireNonNull(id, "id");
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
