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
    void storeAndPollUrl() {
        //given

        //when
        urlRepository.add(anUrl);
        URL url = urlRepository.poll();
        //then
        assertThat(url).isEqualTo(anUrl);
        assertThat(urlRepository.haveStoredURLs()).isFalse();

    }

    @Test
    void shouldPollUrlInCorrectOrder() {
        //given

        //when
        urlRepository.add(anUrl);
        urlRepository.add(anotherUrl);
        URL firstUrl = urlRepository.poll();
        URL secondUrl = urlRepository.poll();
        //then
        assertThat(firstUrl).isEqualTo(anUrl);
        assertThat(secondUrl).isEqualTo(anotherUrl);

    }

    @Test
    void haveStoredURLs() {
        //given
        urlRepository.add(anUrl);

        //when
        //then
        assertThat(urlRepository.haveStoredURLs()).isTrue();

    }

    @Test
    void haventStoredURLs() {
        //given
        //when
        //then
        assertThat(urlRepository.haveStoredURLs()).isFalse();

    }
}