package com.nilsson.vinylrecognition.catno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CatalogueNumberExtractor {
    //    private static final Logger LOG = LogManager.getLogger(CatalogueNumberExtractor.class);
    private static final Logger LOG = LoggerFactory.getLogger(CatalogueNumberExtractor.class);
    public static final String CATALOGUE_REGEX = "([A-Z0-9]+([ \\-][A-Z0-9]+)*){3,}";
    public static final String CATALOGUE_WHOLE_LINE_REGEX = "^" + CATALOGUE_REGEX + "$";
    private static Pattern patternForCatalogue = Pattern.compile(CATALOGUE_REGEX);
    private static Pattern patternForWholeLineCatalogue = Pattern.compile(CATALOGUE_WHOLE_LINE_REGEX);
    private static Set<String> BLOCKED_WORDS = Set.of("STEREO", "APM", "RPM");

    public static String extractCatalogueNumber(String text) {
        LOG.debug(Arrays.stream(text.split(System.lineSeparator())).collect(Collectors.toList()).toString());

        List<String> collect = findCatalogueNumberWholeLine(text);
        collect.addAll(findCatalogueNumberWholeWords(text));
        collect.addAll(findCatalogueNumberWordsWithinLine(text));

        if (collect.isEmpty()) {
            LOG.error("no catalogue number found");
            return null;
        }
        if (collect.size() != 1) {
            LOG.info("multiple catalogue numbers match, attempting to pick correct: {}", collect);

            String picked = collect.stream()
                    .filter(s -> s.length() >= 5 && s.length() <= 14)
                    .findFirst()
                    .orElse(collect.get(0));
            LOG.info("picked=" + picked);
            return picked;
        }
        return collect.get(0);

    }

    private static List<String> findCatalogueNumberWordsWithinLine(String text) {
        return Arrays.stream(text.split(System.lineSeparator()))
                .map(s -> patternForCatalogue.matcher(s))
                .filter(Matcher::find)
                .map(matcher -> matcher.group(0))
//                .filter(s -> s.matches(CATALOGUE_REGEX))
                .peek(LOG::debug)
                .filter(s -> s.matches(".*\\d+.*"))
                .filter(withoutBlockedWords())
                .filter(s -> s.length() > 2)
                .collect(Collectors.toList());
    }

    private static List<String> findCatalogueNumberWholeWords(String text) {
        return Arrays.stream(text.split(System.lineSeparator()))
                .flatMap(s -> Arrays.stream(s.split("\\s")))
                .filter(s -> s.matches(CATALOGUE_REGEX))
                .filter(s -> s.matches(".*\\d+.*"))
                .filter(withoutBlockedWords())
                .filter(s -> s.length() > 2)
                .collect(Collectors.toList());
    }

    private static List<String> findCatalogueNumberWholeLine(String text) {
        return Arrays.stream(text.split(System.lineSeparator()))
                .map(s -> patternForWholeLineCatalogue.matcher(s))
                .filter(Matcher::find)
                .map(matcher -> matcher.group(0))
                .filter(s -> s.matches(CATALOGUE_REGEX))
                .filter(s -> s.matches(".*\\d+.*"))
                .filter(withoutBlockedWords())
                .filter(s -> s.length() > 2)
                .collect(Collectors.toList());
    }

    private static Predicate<String> withoutBlockedWords() {
        return s -> Arrays.stream(s.split("\\s")).noneMatch(BLOCKED_WORDS::contains);
    }

}
