package com.nilsson.vinylrecordsales.lookup;

import com.nilsson.vinylrecordsales.domain.RecordInformation;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface LookupService {

    Optional<RecordInformation> getRecordInformationByCatalogueNumber(String catalogueNumber, String... extraTitleWords);

    Mono<Optional<RecordInformation>> getMonoRecordInformationByCatalogueNumber(String catalogueNumber, String... extraTitleWords);
}
