package com.nilsson.vinylrecordsales.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Year;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class ProductCategoryTest {
    @ParameterizedTest
    @CsvSource({"1950", "1955", "1960", "1969"})
    void get50sAnd60s(Year year) {
        Optional<ProductCategory> category = ProductCategory.fromYear(year);
        assertThat(category).isNotEmpty().hasValue(ProductCategory.DECADE_50S_60S);
    }

    @ParameterizedTest
    @CsvSource({"1970", "1975", "1979"})
    void get70s(Year year) {
        Optional<ProductCategory> category = ProductCategory.fromYear(year);
        assertThat(category).isNotEmpty().hasValue(ProductCategory.DECADE_70S);
    }

    @ParameterizedTest
    @CsvSource({"1980", "1985", "1989"})
    void get80s(Year year) {
        Optional<ProductCategory> category = ProductCategory.fromYear(year);
        assertThat(category).isNotEmpty().hasValue(ProductCategory.DECADE_80S);
    }

    @ParameterizedTest
    @CsvSource({"1990", "1995", "1999"})
    void get90s(Year year) {
        Optional<ProductCategory> category = ProductCategory.fromYear(year);
        assertThat(category).isNotEmpty().hasValue(ProductCategory.DECADE_90S);
    }

    @ParameterizedTest
    @CsvSource({"2000", "2005", "2009", "2015", "2019", "2021"})
    void get2000s(Year year) {
        Optional<ProductCategory> category = ProductCategory.fromYear(year);
        assertThat(category).isNotEmpty().hasValue(ProductCategory.DECADE_2000);
    }

    @ParameterizedTest
    @CsvSource({"1600", "1700", "1800", "1900", "1949", "2121"})
    void getOther(Year year) {
        Optional<ProductCategory> category = ProductCategory.fromYear(year);
        assertThat(category).isEmpty();
    }
}