package com.nilsson.vinylrecordsales.advertisement;

import com.nilsson.vinylrecordsales.domain.AdvertisementInformation;
import com.nilsson.vinylrecordsales.domain.ApiToken;
import com.nilsson.vinylrecordsales.domain.ProductId;
import com.nilsson.vinylrecordsales.lookup.ExampleJsonResponses;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdvertisementFacadeImplTest {
    @Mock
    ApiToken apiToken;
    private MockWebServer mockBackend;
    private AdvertisementFacadeImpl advertisementFacade;

    @BeforeEach
    void setUp() throws IOException {
        mockBackend = new MockWebServer();
        mockBackend.start();
        String baseUrl = format("http://localhost:%s", mockBackend.getPort());
        when(apiToken.getToken()).thenReturn("secretToken");
        advertisementFacade = new AdvertisementFacadeImpl();
    }

    @Test
    void shouldCreateProduct() throws InterruptedException {
        //given
        mockBackend.enqueue(new MockResponse()
                .setBody(ExampleJsonResponses.productCreatedResponse())
                .addHeader("Content-Type", "application/json"));
        AdvertisementInformation advertisementInformation = AdvertisementInformationTestBuilder.populatedAdvertisementInformationBuilder()
                .build();
        //when
        ProductId productId = advertisementFacade.createProduct(advertisementInformation);

        //then
//        RecordedRequest recordedRequest = mockBackend.takeRequest();
//        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
//        assertThat(recordedRequest.getPath()).isEqualTo("/products");
//        assertThat(recordedRequest.getHeader("Authorization")).isEqualTo("secretToken");
//        assertThat(recordedRequest.getBody().toString()).isEqualTo(advertisementInformation.asJsonString())
        assertThat(productId).isEqualTo(new ProductId("14475806"));
    }

}