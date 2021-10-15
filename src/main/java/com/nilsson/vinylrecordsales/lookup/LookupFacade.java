package com.nilsson.vinylrecordsales.lookup;

import com.nilsson.vinylrecordsales.domain.RecordInformation;

import java.util.Optional;

public interface LookupFacade {

    Optional<RecordInformation> getRecordInformationByCatalogueNumber(String catalogueNumber);
}
