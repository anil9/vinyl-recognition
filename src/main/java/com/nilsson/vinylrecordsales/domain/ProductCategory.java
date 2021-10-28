package com.nilsson.vinylrecordsales.domain;

import org.slf4j.Logger;

import java.time.Year;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

public enum ProductCategory {
	DECADE_50S_60S(210901),
	DECADE_70S(210903),
	DECADE_80S(210905),
	DECADE_90S(210907),
	DECADE_2000(302335),
	ROCK(340414),
	POP(340634),
	JAZZ(340511),
	FOLK(210839),
	COUNTRY(340502),
	CHILDREN(210818),
	OTHER(210830);

	private final Integer id;
	private static final Logger LOG = getLogger(ProductCategory.class);

	ProductCategory(Integer id) {
		this.id = requireNonNull(id, "id");
	}

	public static Optional<ProductCategory> fromGenre(String genre) {
		if (genre.equals("Children's")) {
			return Optional.of(CHILDREN);
		}

		return Arrays.stream(ProductCategory.values())
				.filter(productCategory -> productCategory.name().equals(genre.toUpperCase()))
				.findFirst()
				.or(logAndReturnEmpty(genre));
	}

	private static Supplier<Optional<? extends ProductCategory>> logAndReturnEmpty(String genre) {
		return () -> {
			LOG.info("Couldn't match genre {}, returning empty", genre);
			return Optional.empty();
		};
	}

	public static Optional<ProductCategory> fromYear(Year year) {
		if (year.isAfter(Year.of(1949)) && year.isBefore(Year.of(1970))) {
			return Optional.of(DECADE_50S_60S);
		} else if (year.isAfter(Year.of(1969)) && year.isBefore(Year.of(1980))) {
			return Optional.of(DECADE_70S);
		} else if (year.isAfter(Year.of(1979)) && year.isBefore(Year.of(1990))) {
			return Optional.of(DECADE_80S);
		} else if (year.isAfter(Year.of(1989)) && year.isBefore(Year.of(2000))) {
			return Optional.of(DECADE_90S);
		} else if (year.isAfter(Year.of(1999)) && year.isBefore(Year.now().plusYears(1))) {
			return Optional.of(DECADE_2000);
		} else {
			return Optional.empty();
		}

	}

	public Integer getId() {
		return id;
	}
}
