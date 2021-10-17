package com.nilsson.vinylrecordsales.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import static com.nilsson.vinylrecordsales.domain.PickupStrategy.ALLOW_PICKUP;
import static org.assertj.core.api.Assertions.assertThat;

class AdvertisementInformationTest {

	@Test
	void shouldCreateAdvertisement() {
		//given
		final LinkedHashMap<String, String> tracklist = new LinkedHashMap<>();
		tracklist.put("track1", "duration1");
		tracklist.put("track2", "duration2");
		final RecordInformation recordInformation = RecordInformationTestBuilder.populatedRecordInformationBuilder()
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
						new ShippingInformation(ShippingCompany.SCHENKER, new BigDecimal("70"), ALLOW_PICKUP))
				.withQuantityInStock(new Quantity(1))
				.withTax(new Tax(25))
				.withProductCategory(ProductCategory.OTHER)
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