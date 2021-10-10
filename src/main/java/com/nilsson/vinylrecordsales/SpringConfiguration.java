package com.nilsson.vinylrecordsales;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import com.nilsson.vinylrecordsales.domain.ApiTokenFactory;
import com.nilsson.vinylrecordsales.lookup.LookupFacade;
import com.nilsson.vinylrecordsales.lookup.LookupFacadeImpl;

@Configuration
public class SpringConfiguration {

    @Bean
    public LookupFacade lookupFacade() {
        return new LookupFacadeImpl(ApiTokenFactory.getApiToken(), WebClient.create("https://api.discogs.com"));
    }

}
