package com.nilsson.vinylrecordsales.lookup;

import java.util.Optional;

public interface LookupFacade {
    Optional<String> findTitleByCatalogueNumber(String catalogueNumber);
}
