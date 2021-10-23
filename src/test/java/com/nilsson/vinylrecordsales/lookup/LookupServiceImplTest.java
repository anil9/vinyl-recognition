package com.nilsson.vinylrecordsales.lookup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nilsson.vinylrecordsales.domain.RecordInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LookupServiceImplTest {

    private static final Integer RELEASE_ID = 2229646;
    private static final String TITLE = "Lena Philipsson - Kärleken Är Evig.";
    private LookupServiceImpl lookupService;

    @Mock
    private LookupFacade lookupFacade;
    @Mock
    private RecordInformationConverter recordInformationConverter;

    private static final String CATALOGUE_NUMBER = "MLPH 1622";

    @BeforeEach
    void setUp() {
        lookupService = new LookupServiceImpl(lookupFacade, recordInformationConverter);
    }

    @Test
    void shouldReturnRecordInfo() {
        //given
        String releaseInfoResponse = ExampleJsonResponses.releaseInfo();
        JsonObject releaseResponse = new Gson().fromJson(releaseInfoResponse, JsonObject.class);
        when(lookupFacade.findByCatalogueNumber(CATALOGUE_NUMBER)).thenReturn(Mono.just(ExampleJsonResponses.lookupResponse()));
        when(lookupFacade.getByReleaseId(RELEASE_ID)).thenReturn(Mono.just(releaseInfoResponse));
        InOrder inOrder = Mockito.inOrder(lookupFacade, recordInformationConverter);
        //when
        lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER);
        //then
        inOrder.verify(lookupFacade).findByCatalogueNumber(CATALOGUE_NUMBER);
        inOrder.verify(lookupFacade).getByReleaseId(RELEASE_ID);

        verify(recordInformationConverter).getRecordInformation(TITLE, releaseResponse);
        verifyNoMoreInteractions(lookupFacade);
        verifyNoMoreInteractions(recordInformationConverter);
    }

    @Test
    void shouldReturnEmptyRecordInfoWhenResponseContainsDifferentTitles() {
        when(lookupFacade.findByCatalogueNumber(CATALOGUE_NUMBER)).thenReturn(Mono.just(ExampleJsonResponses.ambiguousLookupResponse()));
        //when
        Optional<RecordInformation> recordInformation = lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER);
        //then
        assertThat(recordInformation).isEmpty();
    }

    @Test
    void shouldUseExtraProvidedTitleWordsWhenGatherInfoAndPostAd() {
        //given
        String releaseInfoResponse = ExampleJsonResponses.releaseInfo();
        JsonObject releaseResponse = new Gson().fromJson(releaseInfoResponse, JsonObject.class);
        when(lookupFacade.findByCatalogueNumber(CATALOGUE_NUMBER)).thenReturn(Mono.just(ExampleJsonResponses.ambiguousLookupResponse()));
        when(lookupFacade.getByReleaseId(RELEASE_ID)).thenReturn(Mono.just(releaseInfoResponse));
        InOrder inOrder = Mockito.inOrder(lookupFacade, recordInformationConverter);
        //when
        lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER, "Lena", "Philipsson", "Evig");
        //then
        inOrder.verify(lookupFacade).findByCatalogueNumber(CATALOGUE_NUMBER);
        inOrder.verify(lookupFacade).getByReleaseId(RELEASE_ID);

        verify(recordInformationConverter).getRecordInformation(TITLE, releaseResponse);
        verifyNoMoreInteractions(lookupFacade);
        verifyNoMoreInteractions(recordInformationConverter);
    }

    @Test
    void shouldReturnEmptyRecordIfTitleCantBeDetermined() {
        //given
        String releaseInfoResponse = ExampleJsonResponses.releaseInfo();
        JsonObject releaseResponse = new Gson().fromJson(releaseInfoResponse, JsonObject.class);
        when(lookupFacade.findByCatalogueNumber(CATALOGUE_NUMBER)).thenReturn(Mono.just(ExampleJsonResponses.ambiguousLookupResponse()));
        //when
        Optional<RecordInformation> recordInformation = lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER, "fel", "fel2", "fel3");
        //then
        verify(lookupFacade).findByCatalogueNumber(CATALOGUE_NUMBER);
        verifyNoMoreInteractions(lookupFacade);
        verifyNoInteractions(recordInformationConverter);
        assertThat(recordInformation).isEmpty();
    }
}