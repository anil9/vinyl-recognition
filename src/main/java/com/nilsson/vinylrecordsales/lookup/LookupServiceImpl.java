package com.nilsson.vinylrecordsales.lookup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nilsson.vinylrecordsales.domain.RecordInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Optional;

import static com.nilsson.vinylrecordsales.lookup.ExternalIdentifier.RECORD_TITLE;
import static com.nilsson.vinylrecordsales.lookup.ExternalIdentifier.RELEASE_ID;
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
        if (!hasDistinctTitle(catalogueNumberResponse)) {
            return Optional.empty();
        }
        var chosenRecord = catalogueNumberResponse.get(0).getAsJsonObject();
        var releaseId = extractReleaseId(chosenRecord);
        JsonObject releaseResponse = getResponseFromReleaseId(releaseId);
        return recordInformationConverter.getRecordInformation(chosenRecord, releaseResponse);

    }

    private String extractReleaseId(JsonObject chosenRecord) {
        return chosenRecord.get(RELEASE_ID.toString()).getAsString();
    }

    private JsonObject getResponseFromReleaseId(String releaseId) {
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