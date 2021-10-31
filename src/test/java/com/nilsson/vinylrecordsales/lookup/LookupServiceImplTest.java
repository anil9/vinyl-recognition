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
import reactor.test.StepVerifier;

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
        RecordInformation recordInformation = mock(RecordInformation.class);
        when(recordInformationConverter.getRecordInformation(TITLE, releaseResponse)).thenReturn(Optional.of(recordInformation));
        InOrder inOrder = Mockito.inOrder(lookupFacade, recordInformationConverter);
        //when
        Mono<Optional<RecordInformation>> informationByCatalogueNumber =
                lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER);
        //then
        StepVerifier.create(informationByCatalogueNumber.log())
                .expectNext(Optional.of(recordInformation))
                .verifyComplete();
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
        Optional<RecordInformation> recordInformation =
                lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER).block();
        //then
        assertThat(recordInformation).isEmpty();
    }

    @Test
    void shouldUseExtraProvidedTitleWordsWhenGatherInfo() {
        //given
        String releaseInfoResponse = ExampleJsonResponses.releaseInfo();
        JsonObject releaseResponse = new Gson().fromJson(releaseInfoResponse, JsonObject.class);
        when(lookupFacade.findByCatalogueNumber(CATALOGUE_NUMBER)).thenReturn(Mono.just(ExampleJsonResponses.ambiguousLookupResponse()));
        when(lookupFacade.getByReleaseId(RELEASE_ID)).thenReturn(Mono.just(releaseInfoResponse));
        InOrder inOrder = Mockito.inOrder(lookupFacade, recordInformationConverter);
        //when
        lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER, "Lena", "Philipsson", "Evig").subscribe();
        //then
        inOrder.verify(lookupFacade).findByCatalogueNumber(CATALOGUE_NUMBER);
        inOrder.verify(lookupFacade).getByReleaseId(RELEASE_ID);

        verify(recordInformationConverter).getRecordInformation(TITLE, releaseResponse);
        verifyNoMoreInteractions(lookupFacade);
        verifyNoMoreInteractions(recordInformationConverter);
    }

    @Test
    void providedTitleWordsIsCaseInsensitiveWhenGatheringInfo() {
        //given
        String releaseInfoResponse = ExampleJsonResponses.releaseInfo();
        JsonObject releaseResponse = new Gson().fromJson(releaseInfoResponse, JsonObject.class);
        when(lookupFacade.findByCatalogueNumber(CATALOGUE_NUMBER)).thenReturn(Mono.just(ExampleJsonResponses.ambiguousLookupResponse()));
        when(lookupFacade.getByReleaseId(RELEASE_ID)).thenReturn(Mono.just(releaseInfoResponse));
        InOrder inOrder = Mockito.inOrder(lookupFacade, recordInformationConverter);
        //when
        lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER, "LeNa", "PhIlIpSsoN", "EVIG").subscribe();
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
        when(lookupFacade.findByCatalogueNumber(CATALOGUE_NUMBER)).thenReturn(Mono.just(ExampleJsonResponses.ambiguousLookupResponse()));
        //when
        Optional<RecordInformation> recordInformation =
                lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER, "fel", "fel2", "fel3").block();
        //then
        verify(lookupFacade).findByCatalogueNumber(CATALOGUE_NUMBER);
        verifyNoMoreInteractions(lookupFacade);
        verifyNoInteractions(recordInformationConverter);
        assertThat(recordInformation).isEmpty();
    }

    @Test
    void shouldReturnEmptyRecordIfTitleCantBeDeterminedWithExtraWords() {
        //given
        when(lookupFacade.findByCatalogueNumber(CATALOGUE_NUMBER)).thenReturn(Mono.just(ExampleJsonResponses.ambiguousLookupResponse()));
        //when
        Optional<RecordInformation> recordInformation =
                lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER, "Philipsson").block();
        //then
        verify(lookupFacade).findByCatalogueNumber(CATALOGUE_NUMBER);
        verifyNoMoreInteractions(lookupFacade);
        verifyNoInteractions(recordInformationConverter);
        assertThat(recordInformation).isEmpty();
    }

    @Test
    void shouldReturnEmptyRecordIfAnyProvidedExtraWordDoesntMatchTitle() {
        //given
        when(lookupFacade.findByCatalogueNumber(CATALOGUE_NUMBER)).thenReturn(Mono.just(ExampleJsonResponses.ambiguousLookupResponse()));
        //when
        Optional<RecordInformation> recordInformation =
                lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER, "Lena", "Philipsson", "Evig"
                        , "fel").block();
        //then
        verify(lookupFacade).findByCatalogueNumber(CATALOGUE_NUMBER);
        verifyNoMoreInteractions(lookupFacade);
        verifyNoInteractions(recordInformationConverter);
        assertThat(recordInformation).isEmpty();
    }

    @Test
    void shouldPickReleaseIdFromRelease() {
        //given
        String releaseInfoResponse = ExampleJsonResponses.releaseInfo();
        JsonObject releaseResponse = new Gson().fromJson(releaseInfoResponse, JsonObject.class);
        when(lookupFacade.findByCatalogueNumber(CATALOGUE_NUMBER)).thenReturn(Mono.just(ExampleJsonResponses.lookupResponseContainingMasterRelease()));
        when(lookupFacade.getByReleaseId(RELEASE_ID)).thenReturn(Mono.just(releaseInfoResponse));
        //when
        lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER).subscribe();
        //then
        verify(lookupFacade).findByCatalogueNumber(CATALOGUE_NUMBER);
        verify(lookupFacade).getByReleaseId(RELEASE_ID);

        verify(recordInformationConverter).getRecordInformation(TITLE, releaseResponse);
        verifyNoMoreInteractions(lookupFacade);
        verifyNoMoreInteractions(recordInformationConverter);
    }

    @Test
    void shouldPickReleaseIdFromReleaseWhenProvidedWords() {
        //given
        String releaseInfoResponse = ExampleJsonResponses.releaseInfo();
        JsonObject releaseResponse = new Gson().fromJson(releaseInfoResponse, JsonObject.class);
        when(lookupFacade.findByCatalogueNumber(CATALOGUE_NUMBER)).thenReturn(Mono.just(ExampleJsonResponses.lookupResponseContainingMasterRelease()));
        when(lookupFacade.getByReleaseId(RELEASE_ID)).thenReturn(Mono.just(releaseInfoResponse));
        String extraTitleWords = "Philipsson";
        //when
        lookupService.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER, extraTitleWords).subscribe();
        //then
        verify(lookupFacade).findByCatalogueNumber(CATALOGUE_NUMBER);
        verify(lookupFacade).getByReleaseId(RELEASE_ID);

        verify(recordInformationConverter).getRecordInformation(TITLE, releaseResponse);
        verifyNoMoreInteractions(lookupFacade);
        verifyNoMoreInteractions(recordInformationConverter);
    }
}