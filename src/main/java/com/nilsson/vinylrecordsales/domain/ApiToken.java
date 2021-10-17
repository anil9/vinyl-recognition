package com.nilsson.vinylrecordsales.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

public class ApiToken {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final String token;
    private final TokenProducer tokenProducer;


    public ApiToken(Environment env, TokenProducer tokenProducer) {
        this.tokenProducer = tokenProducer;
        String tokenFromFile = fromFile(env.getProperty(tokenProducer.getProperty())).trim();
        LOG.debug("Read {} token from file", tokenProducer);
        validate(tokenFromFile);
        LOG.debug("Validated {} token", tokenProducer);
        this.token = tokenFromFile;
        LOG.info("Loaded token for {} API", tokenProducer);
    }

    private void validate(String tokenFromFile) {
        if (tokenFromFile.isBlank()) {
            throw new IllegalArgumentException("Token cannot be empty");
        }
    }

    private String fromFile(String path) {
        try (Stream<String> lines = Files.lines(Path.of(path))) {
            return lines.findFirst().orElseThrow(() -> new IllegalStateException("File must contain an API-token"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String getToken() {
        return token;
    }

    public TokenProducer getTokenProducer() {
        return tokenProducer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiToken apiToken = (ApiToken) o;
        return Objects.equals(token, apiToken.token) && tokenProducer == apiToken.tokenProducer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, tokenProducer);
    }

    @Override
    public String toString() {
        return "ApiToken{" +
                "token='" + token + '\'' +
                ", tokenProducer=" + tokenProducer +
                '}';
    }
}
