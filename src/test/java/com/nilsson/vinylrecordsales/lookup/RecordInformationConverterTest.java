package com.nilsson.vinylrecordsales.lookup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nilsson.vinylrecordsales.domain.RecordInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RecordInformationConverterTest {

    private RecordInformationConverter recordInformationConverter;

    @BeforeEach
    void setUp() {
        recordInformationConverter = new RecordInformationConverter();
    }

    @Test
    void shouldConvertToRecordInformation() {
        //given
        JsonObject chosenRecord = new Gson().fromJson(ExampleJsonResponses.record(), JsonObject.class);
        JsonObject releaseResponse = new Gson().fromJson(ExampleJsonResponses.releaseInfo(), JsonObject.class);

        //when
        Optional<RecordInformation> recordInformation = recordInformationConverter.getRecordInformation(chosenRecord, releaseResponse);

        //then
        assertThat(recordInformation).hasValue(RecordInformation.builder()
                .withTitle("Lena Philipsson - Kärleken Är Evig.")
                .withYear(Year.of(1986))
                .withGenre(List.of("Electronic", "Pop"))
                .withStyle(List.of("Synth-pop", "Schlager"))
                .withTracklist(Map.of("Kärleken Är Evig", "3:03",
                        "Åh Amadeus", "3:35"))
                .build());
    }

    @Test
    void shouldConvertToRecordInformationWithoutYear() {
        //given
        JsonObject chosenRecord = new Gson().fromJson(ExampleJsonResponses.record(), JsonObject.class);
        JsonObject releaseResponseWithoutYear = new Gson().fromJson(ExampleJsonResponses.releaseInfoWithoutYear(), JsonObject.class);

        //when
        Optional<RecordInformation> recordInformation = recordInformationConverter.getRecordInformation(chosenRecord, releaseResponseWithoutYear);

        //then
        assertThat(recordInformation).hasValue(RecordInformation.builder()
                .withTitle("Lena Philipsson - Kärleken Är Evig.")
                .withYear(null)
                .withGenre(List.of("Electronic", "Pop"))
                .withStyle(List.of("Synth-pop", "Schlager"))
                .withTracklist(Map.of("Kärleken Är Evig", "3:03",
                        "Åh Amadeus", "3:35"))
                .build());


    }

}