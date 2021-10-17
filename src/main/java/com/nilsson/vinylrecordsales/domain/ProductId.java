package com.nilsson.vinylrecordsales.domain;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class ProductId {
	private final Long id;

	public ProductId(Long id) {
		this.id = requireNonNull(id, "id");
	}

	public Long getId() {
		return id;
	}

	@Override public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProductId productId = (ProductId) o;
		return Objects.equals(getId(), productId.getId());
	}

	@Override public int hashCode() {
		return Objects.hash(getId());
	}

	@Override public String toString() {
		return "ProductId{" +
				"id=" + id +
				'}';
	}
}
