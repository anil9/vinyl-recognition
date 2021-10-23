package com.nilsson.vinylrecordsales.lookup;

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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LookupFacadeImplTest {
    private static MockWebServer mockBackend;
    private LookupFacadeImpl lookupFacadeImpl;

    @Mock
    ApiToken apiToken;

    @BeforeEach
    void setUp() throws IOException {
        mockBackend = new MockWebServer();
        mockBackend.start();
        String baseUrl = String.format("http://localhost:%s",
                mockBackend.getPort());
        when(apiToken.getToken()).thenReturn("secretToken");
        lookupFacadeImpl = new LookupFacadeImpl(apiToken, WebClient.create(baseUrl));
    }


    @Test
    void canRequestDataByCatalogueNumberFromApi() throws InterruptedException {
        //given
        mockBackend.enqueue(new MockResponse()
                .setBody(ExampleJsonResponses.lookupResponse())
                .addHeader("Content-Type", "application/json"));
        //when
        Mono<String> catalogueMono = lookupFacadeImpl.findByCatalogueNumber("test");
        //then
        StepVerifier.create(catalogueMono)
                .expectNextMatches(s -> s.equals(ExampleJsonResponses.lookupResponse()))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackend.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo("/database/search?catno=test");
        assertThat(recordedRequest.getHeader("Authorization")).isEqualTo("Discogs token=secretToken");

    }

    @Test
    void canRequestDataByReleaseIdFromApi() throws InterruptedException {
        //given
        mockBackend.enqueue(new MockResponse()
                .setBody(ExampleJsonResponses.releaseInfo())
                .addHeader("Content-Type", "application/json"));
        //when
        Mono<String> releaseInfoResponse = lookupFacadeImpl.getByReleaseId("releaseId");
        //then
        StepVerifier.create(releaseInfoResponse)
                .expectNextMatches(s -> s.equals(ExampleJsonResponses.releaseInfo()))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackend.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo("/releases/releaseId");
        assertThat(recordedRequest.getHeader("Authorization")).isEqualTo("Discogs token=secretToken");

    }

}