package com.nilsson.vinylrecordsales.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCategoryFactoryTest {

    private ProductCategoryFactory productCategoryFactory;

    @BeforeEach
    void setUp() {
        productCategoryFactory = new ProductCategoryFactory();
    }

    @Test
    void shouldPrioritizeGenreFirst() {
        // when
        ProductCategory productCategory = productCategoryFactory.fromRecordInformation(RecordInformationTestBuilder.populatedRecordInformationBuilder()
                .withGenre(List.of("Rock"))
                .withYear(Year.of(1980))
                .build());
        // then
        assertThat(productCategory).isEqualTo(ProductCategory.ROCK);
    }

    @Test
    void shouldPrioritizeAnyGenreFirst() {
        // when
        ProductCategory productCategory = productCategoryFactory.fromRecordInformation(RecordInformationTestBuilder.populatedRecordInformationBuilder()
                .withGenre(List.of("some", "weird", "rock", "genre"))
                .withYear(Year.of(1980))
                .build());
        // then
        assertThat(productCategory).isEqualTo(ProductCategory.ROCK);
    }

    @Test
    void shouldPrioritizeDecadeSecond() {
        // when
        ProductCategory productCategory = productCategoryFactory.fromRecordInformation(RecordInformationTestBuilder.populatedRecordInformationBuilder()
                .withGenre(List.of("some", "weird", "genre"))
                .withYear(Year.of(1980))
                .build());
        // then
        assertThat(productCategory).isEqualTo(ProductCategory.DECADE_80S);
    }

    @Test
    void shouldReturnOtherIfNothingMatches() {
        // when
        ProductCategory productCategory = productCategoryFactory.fromRecordInformation(RecordInformationTestBuilder.populatedRecordInformationBuilder()
                .withGenre(List.of("some", "weird", "genre"))
                .withYear(null)
                .build());
        // then
        assertThat(productCategory).isEqualTo(ProductCategory.OTHER);
    }
}