package com.nilsson.vinylrecognition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import com.nilsson.vinylrecognition.domain.ApiTokenFactory;
import com.nilsson.vinylrecognition.lookup.LookupFacade;
import com.nilsson.vinylrecognition.lookup.LookupFacadeImpl;

@Configuration
public class SpringConfiguration {

    @Bean
    public LookupFacade lookupFacade() {
        return new LookupFacadeImpl(ApiTokenFactory.getApiToken(), WebClient.create("https://api.discogs.com"));
    }

}
