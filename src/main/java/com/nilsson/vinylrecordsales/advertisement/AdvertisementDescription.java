package com.nilsson.vinylrecordsales.advertisement;

import com.nilsson.vinylrecordsales.domain.RecordInformation;

import java.time.Year;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdvertisementDescription {
	private static final String STANDARD_DESCRIPTION = """
			Skick se bilder. Ej provspelad. Kolla gärna in mina andra annonser och passa på att buda in fler LP-skivor, jag samfraktar gärna!
			Fraktpriser (ca):
			1 skiva 70 kr
			2 skivor 73 kr
			3 skivor 76 kr
			4 skivor 87 kr
			5 skivor 99 kr
			6 skivor 102 kr
			""";

	private AdvertisementDescription() {
	}

	public static String getDescription(RecordInformation recordInformation) {
		String year = "";
		final Optional<Year> optionalYear = recordInformation.getYear();
		if (optionalYear.isPresent()) {
			year = "År: " + optionalYear.get();
		}
		String genre = "";
		if (!recordInformation.getGenre().isEmpty()) {
			genre = "Genre: " + String.join(", ", recordInformation.getGenre());
		}
		String style = "";
		if (!recordInformation.getStyle().isEmpty()) {
			style = "Style: " + String.join(", ", recordInformation.getStyle());
		}
		String tracklist = "";
		if (!recordInformation.getTracklist().isEmpty()) {
			tracklist = "Tracklist:\n";
			tracklist += recordInformation.getTracklist().entrySet().stream()
					.map(entry -> String.join(" ", entry.getKey(), entry.getValue()))
					.collect(Collectors.joining("\n"));
		}
		return Stream.of(STANDARD_DESCRIPTION, year, genre, style, tracklist)
				.filter(s -> !s.isBlank())
				.collect(Collectors.joining("\n"));
	}
}
