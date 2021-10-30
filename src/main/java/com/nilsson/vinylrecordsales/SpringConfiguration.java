package com.nilsson.vinylrecordsales;

import com.cloudinary.Cloudinary;
import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacade;
import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacadeImpl;
import com.nilsson.vinylrecordsales.domain.*;
import com.nilsson.vinylrecordsales.file.FileService;
import com.nilsson.vinylrecordsales.image.ImageService;
import com.nilsson.vinylrecordsales.image.ImageServiceImpl;
import com.nilsson.vinylrecordsales.image.UrlRepository;
import com.nilsson.vinylrecordsales.image.UrlRepositoryImpl;
import com.nilsson.vinylrecordsales.image.upload.CloudinaryFactory;
import com.nilsson.vinylrecordsales.image.upload.ImageUploadFacade;
import com.nilsson.vinylrecordsales.image.upload.ImageUploadFacadeImpl;
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

    @Bean
    public CloudinaryFactory cloudinaryFactory(Environment environment, ApiTokenFactory apiTokenFactory) {
        return new CloudinaryFactory(environment, apiTokenFactory.cloudinaryApiToken());
    }

    @Bean
    public Cloudinary cloudinary(CloudinaryFactory cloudinaryFactory) {
        return cloudinaryFactory.get();
    }

    @Bean
    public ImageUploadFacade imageUploadFacade(Cloudinary cloudinary) {
        return new ImageUploadFacadeImpl(cloudinary);
    }

    @Bean
    public FileService fileService() {
        return new FileService();
    }

    @Bean
    public UrlRepository urlRepository() {
        return new UrlRepositoryImpl();
    }

    @Bean
    public ImageService imageService(ImageUploadFacade imageUploadFacade, UrlRepository urlRepository) {
        return new ImageServiceImpl(imageUploadFacade,
                urlRepository);
    }

}
