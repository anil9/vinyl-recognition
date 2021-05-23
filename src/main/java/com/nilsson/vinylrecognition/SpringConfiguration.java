package com.nilsson.vinylrecognition;

import com.nilsson.vinylrecognition.domain.ApiTokenFactory;
import com.nilsson.vinylrecognition.lookup.LookupFacade;
import com.nilsson.vinylrecognition.lookup.LookupFacadeImpl;
import com.nilsson.vinylrecognition.ocr.OCRFacade;
import com.nilsson.vinylrecognition.ocr.OCRFacadeImpl;
import com.nilsson.vinylrecognition.ocr.TesseractFactory;
import com.nilsson.vinylrecognition.preprocessing.ImagePreProcessorFacade;
import com.nilsson.vinylrecognition.preprocessing.ImagePreProcessorFacadeImpl;
import org.im4java.core.ConvertCmd;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    public OCRFacade ocrFacade() {
        return new OCRFacadeImpl(TesseractFactory.createTesseractInstance());
    }

    @Bean
    public ConvertCmd convertCmd() {
        return new ConvertCmd();
    }

    @Bean
    public ImagePreProcessorFacade imagePreProcessor(ConvertCmd convertCmd) {
        return new ImagePreProcessorFacadeImpl(convertCmd);
    }

    @Bean
    public LookupFacade lookupFacade() {
        return new LookupFacadeImpl(ApiTokenFactory.getApiToken());
    }

    @Bean
    public VinylRecognizer vinylRecognizer(ImagePreProcessorFacade imagePreProcessor, OCRFacade ocrFacade) {
        return new VinylRecognizer(imagePreProcessor, ocrFacade);
    }

}
