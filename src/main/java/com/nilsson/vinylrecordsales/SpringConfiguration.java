package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.domain.ApiTokenFactory;
import com.nilsson.vinylrecordsales.lookup.LookupFacade;
import com.nilsson.vinylrecordsales.lookup.LookupFacadeImpl;
import com.nilsson.vinylrecordsales.lookup.RecordInformationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SpringConfiguration {

    @Bean
    public LookupFacade lookupFacade() {
        return new LookupFacadeImpl(ApiTokenFactory.getApiToken(), WebClient.create("https://api.discogs.com"), new RecordInformationConverter());
    }

}
