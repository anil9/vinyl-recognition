package com.nilsson.vinylrecordsales.domain;

import java.time.Year;
import java.util.List;
import java.util.Map;

public class RecordInformationTestBuilder {
    public static RecordInformation.RecordInformationBuilder populatedRecordInformationBuilder() {
        return RecordInformation.builder()
                .withTitle("Title")
                .withYear(Year.of(1990))
                .withGenre(List.of("genre1", "genre2"))
                .withStyle(List.of("style1", "style2"))
                .withTracklist(Map.of("track1", "duration1"));
    }
}