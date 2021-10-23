package com.nilsson.vinylrecordsales.lookup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nilsson.vinylrecordsales.domain.RecordInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.nilsson.vinylrecordsales.lookup.ExternalIdentifier.*;
import static java.util.Objects.requireNonNull;

public class LookupServiceImpl implements LookupService {
    private final LookupFacade lookupFacade;
    private final RecordInformationConverter recordInformationConverter;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public LookupServiceImpl(LookupFacade lookupFacade, RecordInformationConverter recordInformationConverter) {
        this.lookupFacade = requireNonNull(lookupFacade, "lookupFacade");
        this.recordInformationConverter = requireNonNull(recordInformationConverter, "recordInformationConverter");
    }

    @Override
    public Optional<RecordInformation> getRecordInformationByCatalogueNumber(String catalogueNumber, String... extraTitleWords) {
        JsonArray catalogueNumberResponse = getResponseFromCatalogueNumber(catalogueNumber);
        Optional<Integer> releaseId = findCorrectReleaseId(catalogueNumberResponse, extraTitleWords);
        if (releaseId.isEmpty()) {
            return Optional.empty();
        }
        String title = findTitle(catalogueNumberResponse, releaseId.get());
        JsonObject releaseResponse = getResponseFromReleaseId(releaseId.get());
        return recordInformationConverter.getRecordInformation(title, releaseResponse);

    }

    private String findTitle(JsonArray catalogueNumberResponse, Integer releaseId) {
        return IntStream.range(0, catalogueNumberResponse.size())
                .mapToObj(catalogueNumberResponse::get)
                .map(JsonElement::getAsJsonObject)
                .filter(response -> response.get(RELEASE_ID.toString()).getAsInt() == releaseId)
                .findFirst()
                .map(correctResponse -> correctResponse.get(RECORD_TITLE.toString()))
                .map(JsonElement::getAsString)
                .orElseThrow();
    }

    private Optional<Integer> findCorrectReleaseId(JsonArray catalogueNumberResponse, String... extraTitleWords) {
        if (extraTitleWords.length > 0) {
            return findReleaseIdWithExtraTitleWords(catalogueNumberResponse, extraTitleWords);
        } else {
            if (!hasDistinctTitle(catalogueNumberResponse)) {
                return Optional.empty();
            }
            var chosenRecord = catalogueNumberResponse.get(0).getAsJsonObject();
            return Optional.of(extractReleaseId(chosenRecord));
        }


    }

    private Optional<Integer> findReleaseIdWithExtraTitleWords(JsonArray catalogueNumberResponse, String... extraTitleWords) {
        return IntStream.range(0, catalogueNumberResponse.size())
                .mapToObj(catalogueNumberResponse::get)
                .map(JsonElement::getAsJsonObject)
                .filter(response -> titleMatchesExtraWords(response.get(TRACK_TITLE.toString()).getAsString(), extraTitleWords))
                .findFirst()
                .map(correctResponse -> correctResponse.get(RELEASE_ID.toString()))
                .map(JsonElement::getAsInt);
    }

    private boolean titleMatchesExtraWords(String title, String... extraTitleWords) {
        return Arrays.stream(extraTitleWords).allMatch(title::contains);
    }


    private Integer extractReleaseId(JsonObject chosenRecord) {
        return chosenRecord.get(RELEASE_ID.toString()).getAsInt();
    }

    private JsonObject getResponseFromReleaseId(Integer releaseId) {
        String response = lookupFacade.getByReleaseId(releaseId).block();
        assert response != null;
        LOG.debug(response);
        return JsonParser.parseString(response).getAsJsonObject();
    }

    private JsonArray getResponseFromCatalogueNumber(String catalogueNumber) {
        String response = lookupFacade.findByCatalogueNumber(catalogueNumber).block();
        assert response != null;
        LOG.debug(response);
        var asJsonObject = JsonParser.parseString(response).getAsJsonObject();
        return asJsonObject.getAsJsonArray("results");
    }

    private boolean hasDistinctTitle(JsonArray results) {
        var titles = new HashSet<String>();
        for (JsonElement result : results) {
            var recordObj = result.getAsJsonObject();
            titles.add(recordObj.get(RECORD_TITLE.toString()).getAsString());
        }

        boolean distinctTitle = titles.size() == 1;
        if (!distinctTitle) {
            LOG.warn("Cannot determine which title to use. Found titles <{}>", titles);
        }

        return distinctTitle;
    }
}