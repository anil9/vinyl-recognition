package com.nilsson.vinylrecordsales.lookup;

import com.nilsson.vinylrecordsales.domain.ApiToken;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
    public static MockWebServer mockBackEnd;
    private LookupFacadeImpl lookupFacadeImpl;

    @Mock
    ApiToken apiToken;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        when(apiToken.getValue()).thenReturn("secretToken");
        lookupFacadeImpl = new LookupFacadeImpl(apiToken, WebClient.create(baseUrl));
    }

    @Test
    void canRequestDataFromApi() throws InterruptedException {
        //given
        mockBackEnd.enqueue(new MockResponse()
                .setBody(exampleSuccessJsonResponse())
                .addHeader("Content-Type", "application/json"));
        //when
        Mono<String> catalogueMono = lookupFacadeImpl.findByCatalogueNumber("test");
        //then
        StepVerifier.create(catalogueMono)
                .expectNextMatches(s -> s.equals(exampleSuccessJsonResponse()))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo("/database/search?catno=test");
        assertThat(recordedRequest.getHeader("Authorization")).isEqualTo("Discogs token=secretToken");

    }

    @Test
    void returnsTitle() {
        //given
        mockBackEnd.enqueue(new MockResponse()
                .setBody(exampleSuccessJsonResponse())
                .addHeader("Content-Type", "application/json"));

        //when
        String title = lookupFacadeImpl.findTitleByCatalogueNumber("MLPH 1622").orElseThrow();
        //then
        assertThat(title).isEqualTo("Lena Philipsson - Kärleken Är Evig.");
    }

    private String exampleSuccessJsonResponse() {
        return """
                {
                  "pagination": {
                   "page": 1,
                   "pages": 1,
                   "per_page": 50,
                   "items": 2,
                   "urls": {}
                  },
                  "results": [
                 
                   {
                    "country": "Sweden",
                    "year": "1986",
                    "format": ["Vinyl", "LP", "Album"],
                    "label": ["Mariann", "Mariann Records", "Mariann Records", "Fri Reklam", "KMH Studios", "SIB-Tryck AB, Norsborg", "Grammoplast"],
                    "type": "release",
                    "genre": ["Electronic", "Pop"],
                    "style": ["Synth-pop", "Schlager"],
                    "id": 2229646,
                    "barcode": ["BIEM/n\\u00a9b", "MLPH-1622-A 860414GP BA-GP", "MLPH-1622-B 860414GP BA-GP"],
                    "user_data": {
                     "in_wantlist": false,
                     "in_collection": false
                    },
                    "master_id": 315620,
                    "master_url": "https://api.discogs.com/masters/315620",
                    "uri": "/Lena-Philipsson-K%C3%A4rleken-%C3%84r-Evig/release/2229646",
                    "catno": "MLPH 1622",
                    "title": "Lena Philipsson - K\\u00e4rleken \\u00c4r Evig.",
                    "thumb": "https://img.discogs.com/K-iaOq7zlRC0q5RfRdA9U8VisCE=/fit-in/150x150/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/R-2229646-1320883037.jpeg.jpg",
                    "cover_image": "https://img.discogs.com/58RI-XKBhD42w1SKIJhXvUIMFqA=/fit-in/450x450/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-2229646-1320883037.jpeg.jpg",
                    "resource_url": "https://api.discogs.com/releases/2229646",
                    "community": {
                     "want": 20,
                     "have": 198
                    },
                    "format_quantity": 1,
                    "formats": [{
                     "name": "Vinyl",
                     "qty": "1",
                     "descriptions": ["LP", "Album"]
                    }]
                   },
                 
                   {
                    "country": "Sweden",
                    "year": "1986",
                    "format": ["Vinyl", "LP", "Album"],
                    "label": ["Mariann", "Mariann Records", "Mariann Records", "Fri Reklam", "KMH Studios", "SIB-Tryck AB, Norsborg", "Grammoplast"],
                    "type": "master",
                    "genre": ["Electronic", "Pop"],
                    "style": ["Synth-pop", "Schlager"],
                    "id": 315620,
                    "barcode": ["BIEM/n\\u00a9b", "MLPH-1622-A 860414GP BA-GP", "MLPH-1622-B 860414GP BA-GP"],
                    "user_data": {
                     "in_wantlist": false,
                     "in_collection": false
                    },
                    "master_id": 315620,
                    "master_url": "https://api.discogs.com/masters/315620",
                    "uri": "/Lena-Philipsson-K%C3%A4rleken-%C3%84r-Evig/master/315620",
                    "catno": "MLPH 1622",
                    "title": "Lena Philipsson - K\\u00e4rleken \\u00c4r Evig.",
                    "thumb": "https://img.discogs.com/K-iaOq7zlRC0q5RfRdA9U8VisCE=/fit-in/150x150/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/R-2229646-1320883037.jpeg.jpg",
                    "cover_image": "https://img.discogs.com/58RI-XKBhD42w1SKIJhXvUIMFqA=/fit-in/450x450/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-2229646-1320883037.jpeg.jpg",
                    "resource_url": "https://api.discogs.com/masters/315620",
                    "community": {
                     "want": 24,
                     "have": 207
                    }
                   }
                  ]
                 }
                """;
    }
}