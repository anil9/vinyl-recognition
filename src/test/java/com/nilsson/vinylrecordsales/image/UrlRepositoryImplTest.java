package com.nilsson.vinylrecordsales.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class UrlRepositoryImplTest {

    private UrlRepositoryImpl urlRepository;
    private URL anUrl;
    private URL anotherUrl;

    @BeforeEach
    void setUp() throws MalformedURLException {
        urlRepository = new UrlRepositoryImpl();
        anUrl = new URL("https://httpstat.us/");
        anotherUrl = new URL("https://httpstat2.us/");
    }

    @Test
    void shouldStoreUrlAtCorrectIndex() {
        //given

        //when
        urlRepository.store(anUrl);
        URL url = urlRepository.getURLByInsertionOrderIndex(0);
        //then
        assertThat(url).isEqualTo(anUrl);

    }

    @Test
    void shouldStoreUrlsAtCorrectIndex() {
        //given

        //when
        urlRepository.store(anUrl);
        urlRepository.store(anotherUrl);
        URL firstUrl = urlRepository.getURLByInsertionOrderIndex(0);
        URL secondUrl = urlRepository.getURLByInsertionOrderIndex(1);
        //then
        assertThat(firstUrl).isEqualTo(anUrl);
        assertThat(secondUrl).isEqualTo(anotherUrl);

    }
}