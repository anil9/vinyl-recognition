package org.nilsson.catno;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogueNumberExtractor {
    private static final Logger LOG = LogManager.getLogger(CatalogueNumberExtractor.class);

    public static String extractCatalogueNumber(String text) {

        LOG.debug(Arrays.stream(text.split(System.lineSeparator())).collect(Collectors.toList()));

        List<String> collect = Arrays.stream(text.split(System.lineSeparator()))
                .filter(s -> s.matches("[A-Z0-9]+([ \\-][A-Z0-9]+)*"))
                .filter(s -> s.matches(".*\\d+.*"))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            LOG.error("no catalogue number found");
            return null;
        }
        if (collect.size() != 1) {
            LOG.info("multiple catalogue numbers match, picking first of: " + collect);
        }
        return collect.get(0);

    }

}
