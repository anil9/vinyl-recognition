package com.nilsson.vinylrecordsales.lookup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LookupServiceImplTest {

    private static final String RELEASE_ID = "2229646";
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
        String recordResponse = ExampleJsonResponses.record();
        JsonObject chosenRecord = new Gson().fromJson(recordResponse, JsonObject.class);
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

        verify(recordInformationConverter).getRecordInformation(chosenRecord, releaseResponse);
        verifyNoMoreInteractions(lookupFacade);
        verifyNoMoreInteractions(recordInformationConverter);
    }
}