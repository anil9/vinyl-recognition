package com.nilsson.vinylrecordsales.domain;

import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Year;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;

public enum ProductCategory {
	DECADE_50S_60S(210901),
	DECADE_70S(210903),
	DECADE_80S(210905),
	DECADE_90S(210907),
	DECADE_2000(302335),
	ROCK(210831),
	POP(340559),
	JAZZ(210809),
	FOLK(210839),
	COUNTRY(210805),
	CHILDREN(210818),
	OTHER(210830);

	private final Integer id;
    private static final Logger LOG = getLogger(ProductCategory.class);

	ProductCategory(Integer id) {
		this.id = requireNonNull(id, "id");
	}

	public static ProductCategory fromGenre(String genre) {
        final Optional<ProductCategory> matchedByInput = Arrays.stream(ProductCategory.values())
                .filter(productCategory -> productCategory.name().equals(genre.toUpperCase()))
                .findFirst();
        if(matchedByInput.isPresent()){
            return matchedByInput.get();
        } else if(genre.equals("Children's")){
		    return CHILDREN;
        } else {
            LOG.info("Couldn't match genre {}, returning OTHER", genre);
            return OTHER;
        }
	}

	public static ProductCategory fromYear(Year year) {
		if (year.isAfter(Year.of(1949)) && year.isBefore(Year.of(1970))) {
			return DECADE_50S_60S;
		} else if (year.isAfter(Year.of(1969)) && year.isBefore(Year.of(1980))) {
			return DECADE_70S;
		} else if (year.isAfter(Year.of(1979)) && year.isBefore(Year.of(1990))) {
			return DECADE_80S;
		} else if (year.isAfter(Year.of(1989)) && year.isBefore(Year.of(2000))) {
			return DECADE_90S;
		} else if (year.isAfter(Year.of(1999)) && year.isBefore(Year.now().plusYears(1))) {
			return DECADE_2000;
		} else {
			return OTHER;
		}

	}

	public Integer getId() {
		return id;
	}
}
