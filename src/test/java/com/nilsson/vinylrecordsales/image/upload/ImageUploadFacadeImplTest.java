package com.nilsson.vinylrecordsales.image.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageUploadFacadeImplTest {


    private ImageUploadFacadeImpl imageUploadFacade;

    @Mock
    private File file;
    @Mock
    private Cloudinary cloudinary;
    @Mock
    private Uploader uploader;

    @BeforeEach
    void setUp() {
        imageUploadFacade = new ImageUploadFacadeImpl(cloudinary);
    }

    @Test
    void shouldUploadImage() throws IOException {
        //given
        String secureUrlString = "https://httpstat.us/";
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(file, ObjectUtils.emptyMap())).thenReturn(Map.of("any", "thing", "secure_url", secureUrlString));
        //when
        URL url = imageUploadFacade.uploadImage(file).block();
        //then
        assertThat(url).isEqualTo(new URL(secureUrlString));
        verify(cloudinary).uploader();
        verify(uploader).upload(eq(file), anyMap());
        verifyNoMoreInteractions(cloudinary);
        verifyNoMoreInteractions(uploader);
    }

    @Test
    void shouldThrowException() throws IOException {
        //given
        String badUrl = "weird5544url";
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(file, ObjectUtils.emptyMap())).thenReturn(Map.of("any", "thing", "secure_url", badUrl));
        //when
        Mono<URL> urlMono = imageUploadFacade.uploadImage(file);
        //then
        assertThrows(IllegalArgumentException.class, urlMono::block);


    }
}