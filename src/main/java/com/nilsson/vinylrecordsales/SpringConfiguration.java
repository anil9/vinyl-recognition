package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacade;
import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacadeImpl;
import com.nilsson.vinylrecordsales.domain.AdvertisementInformationConverter;
import com.nilsson.vinylrecordsales.domain.ApiTokenFactory;
import com.nilsson.vinylrecordsales.domain.WebClientFactory;
import com.nilsson.vinylrecordsales.lookup.LookupFacade;
import com.nilsson.vinylrecordsales.lookup.LookupFacadeImpl;
import com.nilsson.vinylrecordsales.lookup.RecordInformationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/application.properties")
public class SpringConfiguration {


    @Bean
    public AdvertisementFacade advertisementFacade(ApiTokenFactory apiTokenFactory, WebClientFactory webClientFactory) {
        return new AdvertisementFacadeImpl(apiTokenFactory.selloApiToken(), webClientFactory.selloWebClient(), new AdvertisementInformationConverter());
    }

    @Bean
    public LookupFacade lookupFacade(ApiTokenFactory apiTokenFactory, WebClientFactory webClientFactory) {
        return new LookupFacadeImpl(apiTokenFactory.discogsApiToken(), webClientFactory.discogsWebClient(), new RecordInformationConverter());
    }

    @Bean
    public ApiTokenFactory apiTokenFactory(Environment environment) {
        return new ApiTokenFactory(environment);
    }

    @Bean
    public WebClientFactory webClientFactory(Environment environment) {
        return new WebClientFactory(environment);
    }


}
