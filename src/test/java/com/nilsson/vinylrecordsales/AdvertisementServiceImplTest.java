package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacade;
import com.nilsson.vinylrecordsales.advertisement.AdvertisementInformationTestBuilder;
import com.nilsson.vinylrecordsales.domain.*;
import com.nilsson.vinylrecordsales.lookup.LookupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdvertisementServiceImplTest {

    private static final ProductId PRODUCT_ID = new ProductId(11);
    @Mock
    private LookupService lookupService;

    @Mock
    private AdvertisementFacade advertisementFacade;

    @Mock
    private AdvertisementInformationFactory adFactory;


    private AdvertisementServiceImpl advertisementService;

    @BeforeEach
    void setUp() {
        advertisementService = new AdvertisementServiceImpl(lookupService, advertisementFacade, adFactory);
    }

    @Test
    void shouldGatherInfoAndPostAd() {
        //given
        String catalogueNumber = "catalogueNumber";
        InOrder inOrder = inOrder(lookupService, adFactory, advertisementFacade);
        Optional<RecordInformation> recordInformation = Optional.of(RecordInformationTestBuilder.populatedRecordInformationBuilder().build());
        when(lookupService.getRecordInformationByCatalogueNumber(catalogueNumber)).thenReturn(Mono.just(recordInformation));
        AdvertisementInformation ad = AdvertisementInformationTestBuilder.populatedAdvertisementInformationBuilder().build();
        when(adFactory.fromTemplate(recordInformation.orElseThrow())).thenReturn(ad);
        when(advertisementFacade.monoCreateProduct(ad)).thenReturn(Mono.just(PRODUCT_ID));
        //when
        advertisementService.createAdvertisement(catalogueNumber).subscribe();
        //then

        inOrder.verify(lookupService).getRecordInformationByCatalogueNumber(catalogueNumber);
        inOrder.verify(adFactory).fromTemplate(recordInformation.orElseThrow());
        inOrder.verify(advertisementFacade).monoCreateProduct(ad);
    }

    @Test
    void shouldUseExtraTitleWordsAndGatherInfoAndPostAd() {
        //given
        String catalogueNumber = "catalogueNumber";
        InOrder inOrder = inOrder(lookupService, adFactory, advertisementFacade);
        Optional<RecordInformation> recordInformation = Optional.of(RecordInformationTestBuilder.populatedRecordInformationBuilder().build());
        String extraTitleWord = "extra";
        when(lookupService.getRecordInformationByCatalogueNumber(catalogueNumber, extraTitleWord)).thenReturn(Mono.just(recordInformation));
        AdvertisementInformation ad = AdvertisementInformationTestBuilder.populatedAdvertisementInformationBuilder().build();
        when(adFactory.fromTemplate(recordInformation.orElseThrow())).thenReturn(ad);
        when(advertisementFacade.monoCreateProduct(ad)).thenReturn(Mono.just(PRODUCT_ID));

        //when
        advertisementService.createAdvertisement(catalogueNumber, extraTitleWord).subscribe();
        //then

        inOrder.verify(lookupService).getRecordInformationByCatalogueNumber(catalogueNumber, extraTitleWord);
        inOrder.verify(adFactory).fromTemplate(recordInformation.orElseThrow());
        inOrder.verify(advertisementFacade).monoCreateProduct(ad);
    }

    @Test
    void callFacadeToAddImagesToProduct() throws MalformedURLException {
        //given
        URL anURL = new URL("https://httpstat.us/");
        URL anotherURL = new URL("https://another-url.us/");
        final Flux<URL> imageUrls = Flux.just(anURL, anotherURL);
        when(advertisementFacade.addImagesToProduct(any(), any())).thenReturn(imageUrls);
        //when
        final Flux<URL> respondingURLs = advertisementService.addImages(PRODUCT_ID, imageUrls);
        //then
        StepVerifier.create(respondingURLs)
                .expectNext(anURL, anotherURL)
                .verifyComplete();
    }
}