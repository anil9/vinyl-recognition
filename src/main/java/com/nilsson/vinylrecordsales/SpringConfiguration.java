package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacade;
import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacadeImpl;
import com.nilsson.vinylrecordsales.domain.AdvertisementInformationConverter;
import com.nilsson.vinylrecordsales.domain.ApiTokenFactory;
import com.nilsson.vinylrecordsales.lookup.LookupFacade;
import com.nilsson.vinylrecordsales.lookup.LookupFacadeImpl;
import com.nilsson.vinylrecordsales.lookup.RecordInformationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@PropertySource("classpath:/application.properties")
public class SpringConfiguration {


    @Bean
    public AdvertisementFacade advertisementFacade(ApiTokenFactory apiTokenFactory) {
        return new AdvertisementFacadeImpl(apiTokenFactory.selloApiToken(), WebClient.create("https://api.sello.io/v5/"), new AdvertisementInformationConverter());
    }

    @Bean
    public LookupFacade lookupFacade(ApiTokenFactory apiTokenFactory) {
        return new LookupFacadeImpl(apiTokenFactory.discogsApiToken(), WebClient.create("https://api.discogs.com"), new RecordInformationConverter());
    }

    @Bean
    public ApiTokenFactory apiTokenFactory(Environment environment) {
        return new ApiTokenFactory(environment);
    }


}
