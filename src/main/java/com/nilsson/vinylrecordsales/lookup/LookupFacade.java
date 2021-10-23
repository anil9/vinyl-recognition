package com.nilsson.vinylrecordsales.lookup;

import reactor.core.publisher.Mono;

public interface LookupFacade {

    Mono<String> findByCatalogueNumber(String catalogueNumber);

    Mono<String> getByReleaseId(String releaseId);
}
