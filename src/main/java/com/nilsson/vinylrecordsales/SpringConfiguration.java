package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacade;
import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacadeImpl;
import com.nilsson.vinylrecordsales.domain.*;
import com.nilsson.vinylrecordsales.lookup.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/application.properties")
public class SpringConfiguration {


    @Bean
    public AdvertisementFacade advertisementFacade(ApiTokenFactory apiTokenFactory, WebClientFactory webClientFactory) {
        return new AdvertisementFacadeImpl(apiTokenFactory.selloApiToken(),
                webClientFactory.selloWebClient(),
                new AdvertisementInformationConverter());
    }

    @Bean
    public LookupFacade lookupFacade(ApiTokenFactory apiTokenFactory, WebClientFactory webClientFactory) {
        return new LookupFacadeImpl(apiTokenFactory.discogsApiToken(),
                webClientFactory.discogsWebClient());
    }

    @Bean
    public ApiTokenFactory apiTokenFactory(Environment environment) {
        return new ApiTokenFactory(environment);
    }

    @Bean
    public WebClientFactory webClientFactory(Environment environment) {
        return new WebClientFactory(environment);
    }

    @Bean
    public AdvertisementInformationFactory advertisementInformationFactory(Environment environment, ProductCategoryFactory productCategoryFactory) {
        return new AdvertisementInformationFactory(environment, productCategoryFactory);
    }

    @Bean
    public ProductCategoryFactory productCategoryFactory() {
        return new ProductCategoryFactory();
    }

    @Bean
    public LookupService lookupService(LookupFacade lookupFacade) {
        return new LookupServiceImpl(lookupFacade, new RecordInformationConverter());
    }

    @Bean
    public CreateAdvertisementService createAdvertisementService(LookupService lookupService,
                                                                 AdvertisementFacade advertisementFacade,
                                                                 AdvertisementInformationFactory adFactory
    ) {
        return new CreateAdvertisementServiceImpl(lookupService, advertisementFacade, adFactory);
    }


}
