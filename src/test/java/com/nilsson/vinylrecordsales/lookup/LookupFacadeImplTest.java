package com.nilsson.vinylrecordsales.lookup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nilsson.vinylrecordsales.domain.ApiToken;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LookupFacadeImplTest {
    private static final String CATALOGUE_NUMBER = "MLPH 1622";
    private static MockWebServer mockBackEnd;
    private LookupFacadeImpl lookupFacadeImpl;

    @Mock
    ApiToken apiToken;
    @Mock
    RecordInformationConverter recordInformationConverter;

    @BeforeEach
    void initialize() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        when(apiToken.getValue()).thenReturn("secretToken");
        lookupFacadeImpl = new LookupFacadeImpl(apiToken, WebClient.create(baseUrl), recordInformationConverter);
    }


    @Test
    void canRequestDataByCatalogueNumberFromApi() throws InterruptedException {
        //given
        mockBackEnd.enqueue(new MockResponse()
                .setBody(ExampleJsonResponses.exampleSuccessJsonResponse())
                .addHeader("Content-Type", "application/json"));
        //when
        Mono<String> catalogueMono = lookupFacadeImpl.findByCatalogueNumber("test");
        //then
        StepVerifier.create(catalogueMono)
                .expectNextMatches(s -> s.equals(ExampleJsonResponses.exampleSuccessJsonResponse()))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo("/database/search?catno=test");
        assertThat(recordedRequest.getHeader("Authorization")).isEqualTo("Discogs token=secretToken");

    }

    @Test
    void canRequestDataByReleaseIdFromApi() throws InterruptedException {
        //given
        mockBackEnd.enqueue(new MockResponse()
                .setBody(ExampleJsonResponses.releaseInfo())
                .addHeader("Content-Type", "application/json"));
        //when
        Mono<String> releaseInfoResponse = lookupFacadeImpl.getByReleaseId("releaseId");
        //then
        StepVerifier.create(releaseInfoResponse)
                .expectNextMatches(s -> s.equals(ExampleJsonResponses.releaseInfo()))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo("/releases/releaseId");
        assertThat(recordedRequest.getHeader("Authorization")).isEqualTo("Discogs token=secretToken");

    }

    @Test
    void returnsRecordInformation() {
        //given
        mockBackEnd.enqueue(new MockResponse()
                .setBody(ExampleJsonResponses.exampleSuccessJsonResponse())
                .addHeader("Content-Type", "application/json"));
        mockBackEnd.enqueue(new MockResponse()
                .setBody(ExampleJsonResponses.releaseInfo())
                .addHeader("Content-Type", "application/json"));
        JsonObject chosenRecord = new Gson().fromJson(ExampleJsonResponses.record(), JsonObject.class);
        JsonObject releaseResponse = new Gson().fromJson(ExampleJsonResponses.releaseInfo(), JsonObject.class);
        //when
        lookupFacadeImpl.getRecordInformationByCatalogueNumber(CATALOGUE_NUMBER);
        //then
        verify(recordInformationConverter).getRecordInformation(chosenRecord, releaseResponse);
        verifyNoMoreInteractions(recordInformationConverter);
    }

}