package com.nilsson.vinylrecordsales.domain;

import java.util.Optional;
import java.util.function.Supplier;

public class ProductCategoryFactory {

    public ProductCategory fromRecordInformation(RecordInformation recordInformation) {
        return recordInformation.getGenre().stream()
                .map(ProductCategory::fromGenre)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .or(getDecade(recordInformation))
                .orElse(ProductCategory.OTHER);
    }

    private Supplier<Optional<? extends ProductCategory>> getDecade(RecordInformation recordInformation) {
        return () -> recordInformation.getYear().flatMap(ProductCategory::fromYear);
    }
}
