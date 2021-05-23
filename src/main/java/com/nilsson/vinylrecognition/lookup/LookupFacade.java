package com.nilsson.vinylrecognition.lookup;

import java.util.Optional;

public interface LookupFacade {
    Optional<String> findTitleByCatalogueNumber(String catalogueNumber);
}
