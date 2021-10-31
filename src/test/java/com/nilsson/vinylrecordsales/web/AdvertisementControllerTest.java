package com.nilsson.vinylrecordsales.web;

import com.nilsson.vinylrecordsales.AdvertisementService;
import com.nilsson.vinylrecordsales.domain.ProductId;
import com.nilsson.vinylrecordsales.image.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementControllerTest {

    @Mock
    AdvertisementService advertisementService;
    @Mock
    ImageService imageService;
    @Mock
    Model model;

    private AdvertisementController advertisementController;
    private URL url;

    @BeforeEach
    void setUp() throws MalformedURLException {
        advertisementController = new AdvertisementController(advertisementService, imageService);
        url = new URL("https://httpstat.us");
    }

    @Test
    void createAdAndAddImages() {
        //given
        final RecordFinder recordFinder = new RecordFinder();
        recordFinder.setCatalogueId("aCatalogueId");
        when(imageService.haveStoredURLs()).thenReturn(true);
        final Mono<ProductId> productId = Mono.just(new ProductId(11));
        when(advertisementService.createAdvertisement(recordFinder.getCatalogueId(), null)).thenReturn(productId);
        final Flux<URL> urls = Flux.just(url, url, url, url, url);
        when(advertisementService.addImages(any(), any())).thenReturn(urls);
        //when
        advertisementController.createAd(recordFinder, model);
        //then
        verify(imageService).haveStoredURLs();
        verifyNoMoreInteractions(imageService);
        verify(advertisementService).createAdvertisement(recordFinder.getCatalogueId(), null);
        verify(advertisementService).addImages(any(), any());
        verifyNoMoreInteractions(advertisementService);

    }

    @Test
    void dontAddImagesIfAdWasntCreated() {
        //given
        final RecordFinder recordFinder = new RecordFinder();
        recordFinder.setCatalogueId("aCatalogueId");
        when(imageService.haveStoredURLs()).thenReturn(true);
        final Mono<ProductId> productId = Mono.empty();
        when(advertisementService.createAdvertisement(recordFinder.getCatalogueId(), null)).thenReturn(productId);
        //when
        advertisementController.createAd(recordFinder, model);
        //then
        verify(imageService).haveStoredURLs();
        verifyNoMoreInteractions(imageService);
        verify(advertisementService).createAdvertisement(recordFinder.getCatalogueId(), null);
        verifyNoMoreInteractions(advertisementService);

    }
}