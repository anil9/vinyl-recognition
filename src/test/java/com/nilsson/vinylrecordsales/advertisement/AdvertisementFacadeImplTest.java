package com.nilsson.vinylrecordsales.advertisement;

import com.nilsson.vinylrecordsales.domain.AdvertisementInformation;
import com.nilsson.vinylrecordsales.domain.AdvertisementInformationConverter;
import com.nilsson.vinylrecordsales.domain.ApiToken;
import com.nilsson.vinylrecordsales.domain.ProductId;
import com.nilsson.vinylrecordsales.lookup.ExampleJsonResponses;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.URL;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdvertisementFacadeImplTest {
    @Mock
    private ApiToken apiToken;
    @Mock
    private AdvertisementInformationConverter converter;
    @Mock
    private JSONObject mockedJSON;


    private MockWebServer mockBackend;
    private AdvertisementFacadeImpl advertisementFacade;

    @BeforeEach
    void setUp() throws IOException {
        mockBackend = new MockWebServer();
        mockBackend.start();
        String baseUrl = format("http://localhost:%s", mockBackend.getPort());
        when(apiToken.getToken()).thenReturn("secretToken");
        advertisementFacade = new AdvertisementFacadeImpl(apiToken, WebClient.create(baseUrl), converter);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackend.shutdown();
    }

    @Test
    void shouldCreateProduct() throws InterruptedException {
        //given
        mockBackend.enqueue(new MockResponse()
                .setBody(ExampleJsonResponses.productCreatedResponse())
                .addHeader("Content-Type", "application/json"));
        AdvertisementInformation advertisementInformation = AdvertisementInformationTestBuilder.populatedAdvertisementInformationBuilder()
                .build();
        when(converter.asJson(advertisementInformation)).thenReturn(mockedJSON);
        String expectedJSONRequest = "mockedJson";
        when(mockedJSON.toString()).thenReturn(expectedJSONRequest);
        //when
        ProductId productId = advertisementFacade.createProduct(advertisementInformation);

        //then
        ProductId expectedProductId = new ProductId(14475806);
        assertThat(productId).isEqualTo(expectedProductId);
        RecordedRequest recordedRequest = mockBackend.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        assertThat(recordedRequest.getPath()).isEqualTo("/products");
        assertThat(recordedRequest.getHeader("Authorization")).isEqualTo("secretToken");
        assertThat(recordedRequest.getBody().readUtf8()).isEqualTo(expectedJSONRequest);
    }

    @Test
    void shouldThrowExceptionWhenBadRequest() {
        mockBackend.enqueue(new MockResponse()
                .setBody(ExampleJsonResponses.productCreatedResponse())
                .setResponseCode(HttpStatus.BAD_REQUEST.value())
                .addHeader("Content-Type", "application/json"));
        when(converter.asJson(any(AdvertisementInformation.class))).thenReturn(mockedJSON);
        String expectedJSONRequest = "mockedJson";
        when(mockedJSON.toString()).thenReturn(expectedJSONRequest);
        AdvertisementInformation advertisementInformation = AdvertisementInformationTestBuilder.populatedAdvertisementInformationBuilder()
                .build();
        //when
        AdvertisementFacadeException advertisementFacadeException = assertThrows(AdvertisementFacadeException.class,
                () -> advertisementFacade.createProduct(advertisementInformation));
        assertThat(advertisementFacadeException.getMessage()).isEqualTo(format("Error creating product. advertisementInformation=%s", advertisementInformation));
    }

    @Test
    void shouldAddImages() throws Exception {
        //given
        mockBackend.enqueue(new MockResponse()
                .setBody(ExampleJsonResponses.imageAddedToProduct())
                .addHeader("Content-Type", "application/json"));
        final URL expectedURL = new URL("https://httpstat.us/");
        final Flux<URL> imageUrls = Flux.just(expectedURL);
        when(converter.asJson(imageUrls)).thenReturn(mockedJSON);
        String expectedJSONRequest = "mockedJson";
        when(mockedJSON.toString()).thenReturn(expectedJSONRequest);
        //when
        final Flux<URL> urlFlux = advertisementFacade.addImagesToProduct(new ProductId(11), imageUrls);

        //then
        StepVerifier.create(urlFlux)
                .expectNext(expectedURL)
                .verifyComplete();
        RecordedRequest recordedRequest = mockBackend.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        assertThat(recordedRequest.getPath()).isEqualTo("/products/11/images");
        assertThat(recordedRequest.getHeader("Authorization")).isEqualTo("secretToken");
        assertThat(recordedRequest.getBody().readUtf8()).isEqualTo(expectedJSONRequest);

    }
}