package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacade;
import com.nilsson.vinylrecordsales.advertisement.AdvertisementInformationTestBuilder;
import com.nilsson.vinylrecordsales.domain.AdvertisementInformation;
import com.nilsson.vinylrecordsales.domain.AdvertisementInformationFactory;
import com.nilsson.vinylrecordsales.domain.RecordInformation;
import com.nilsson.vinylrecordsales.domain.RecordInformationTestBuilder;
import com.nilsson.vinylrecordsales.lookup.LookupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAdvertisementServiceImplTest {

    @Mock
    private LookupService lookupService;

    @Mock
    private AdvertisementFacade advertisementFacade;

    @Mock
    private AdvertisementInformationFactory adFactory;


    private CreateAdvertisementServiceImpl createAdvertisementService;

    @BeforeEach
    void setUp() {
        createAdvertisementService = new CreateAdvertisementServiceImpl(lookupService, advertisementFacade, adFactory);
    }

    @Test
    void shouldGatherInfoAndPostAd() {
        //given
        String catalogueNumber = "catalogueNumber";
        InOrder inOrder = inOrder(lookupService, adFactory, advertisementFacade);
        Optional<RecordInformation> recordInformation = Optional.of(RecordInformationTestBuilder.populatedRecordInformationBuilder().build());
        when(lookupService.getRecordInformationByCatalogueNumber(catalogueNumber)).thenReturn(recordInformation);
        AdvertisementInformation ad = AdvertisementInformationTestBuilder.populatedAdvertisementInformationBuilder().build();
        when(adFactory.fromTemplate(recordInformation.orElseThrow())).thenReturn(ad);
        //when
        createAdvertisementService.createAdvertisement(catalogueNumber);
        //then

        inOrder.verify(lookupService).getRecordInformationByCatalogueNumber(catalogueNumber);
        inOrder.verify(adFactory).fromTemplate(recordInformation.orElseThrow());
        inOrder.verify(advertisementFacade).createProduct(ad);
    }

    @Test
    void shouldUseExtraTitleWordsAndGatherInfoAndPostAd() {
        //given
        String catalogueNumber = "catalogueNumber";
        InOrder inOrder = inOrder(lookupService, adFactory, advertisementFacade);
        Optional<RecordInformation> recordInformation = Optional.of(RecordInformationTestBuilder.populatedRecordInformationBuilder().build());
        String extraTitleWord = "extra";
        when(lookupService.getRecordInformationByCatalogueNumber(catalogueNumber, extraTitleWord)).thenReturn(recordInformation);
        AdvertisementInformation ad = AdvertisementInformationTestBuilder.populatedAdvertisementInformationBuilder().build();
        when(adFactory.fromTemplate(recordInformation.orElseThrow())).thenReturn(ad);
        //when
        createAdvertisementService.createAdvertisement(catalogueNumber, extraTitleWord);
        //then

        inOrder.verify(lookupService).getRecordInformationByCatalogueNumber(catalogueNumber, extraTitleWord);
        inOrder.verify(adFactory).fromTemplate(recordInformation.orElseThrow());
        inOrder.verify(advertisementFacade).createProduct(ad);
    }
}