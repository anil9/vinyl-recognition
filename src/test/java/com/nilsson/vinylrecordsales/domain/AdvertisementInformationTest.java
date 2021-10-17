package com.nilsson.vinylrecordsales.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Year;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class AdvertisementInformationTest {

	@Test
	void shouldCreateAdvertisement() {
		//given
		final LinkedHashMap<String, String> tracklist = new LinkedHashMap<>();
		tracklist.put("track1", "duration1");
		tracklist.put("track2", "duration2");
		final RecordInformation recordInformation = RecordInformation.builder()
				.withTitle("Title")
				.withYear(Year.of(1990))
				.withGenre(List.of("genre1", "genre2"))
				.withStyle(List.of("style1", "style2"))
				.withTracklist(tracklist)
				.build();
		//when
		final AdvertisementInformation ad = AdvertisementInformation.builder()
				.withRecordInformation(recordInformation)
				.withAuctionPrice(BigDecimal.TEN)
				.withCondition(Condition.USED)
				.withFolderId("folderId")
				.withPurchasePrice(BigDecimal.ONE)
				.withShippingInformation(
						new ShippingInformation(ShippingCompany.SCHENKER, new BigDecimal("70"), Boolean.TRUE))
				.withQuantityInStock(new Quantity(1))
				.withTax(25)
				.build();
		//then
		assertThat(ad.getTitle()).isEqualTo(recordInformation.getTitle());
		assertThat(ad.getDescription()).isEqualTo("""
				Skick se bilder. Ej provspelad. Kolla gärna in mina andra annonser och passa på att buda in fler LP-skivor, jag samfraktar gärna!
				Fraktpriser (ca):
				1 skiva 70 kr
				2 skivor 73 kr
				3 skivor 76 kr
				4 skivor 87 kr
				5 skivor 99 kr
				6 skivor 102 kr
								
				År: 1990
				Genre: genre1, genre2
				Style: style1, style2
				Tracklist:
				track1 duration1
				track2 duration2""");
	}
}