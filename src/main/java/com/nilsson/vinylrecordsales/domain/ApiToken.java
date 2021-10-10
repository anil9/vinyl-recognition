package com.nilsson.vinylrecordsales.domain;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

public class ApiToken {
    private final String value;

    public ApiToken(String value) {
        this.value = value;
    }

    public static ApiToken fromFile(String s) {
        try (Stream<String> lines = Files.lines(Path.of(s))) {
            String token = lines.findFirst().orElseThrow(() -> new IllegalStateException("File must contain an API-token"));
            return new ApiToken(token);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiToken apiToken = (ApiToken) o;
        return Objects.equals(value, apiToken.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
