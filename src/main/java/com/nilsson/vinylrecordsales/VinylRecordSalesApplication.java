package com.nilsson.vinylrecordsales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootApplication
public class VinylRecordSalesApplication {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext();
        context.register(SpringConfiguration.class);
        context.refresh();
        SpringApplication.run(VinylRecordSalesApplication.class, args);

//        var catalogueNumber = "LBLP 008";
//        CreateAdvertisementService advertisementService = context.getBean(CreateAdvertisementService.class);
//        advertisementService.createAdvertisement(catalogueNumber);
//        System.exit(0);

    }

}
