package com.nilsson.vinylrecordsales.lookup;

import com.nilsson.vinylrecordsales.domain.RecordInformation;

import java.util.Optional;

public interface LookupService {

    Optional<RecordInformation> getRecordInformationByCatalogueNumber(String catalogueNumber, String... extraTitleWords);
}
