package com.nilsson.vinylrecognition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class VinylRecognitionApplication {

	public static void main(String[] args) throws IOException {
		var context = new AnnotationConfigApplicationContext();
		context.register(SpringConfiguration.class);
		context.refresh();

		SpringApplication.run(VinylRecognitionApplication.class, args);

//		var vinylRecognizer = context.getBean(VinylRecognizer.class);
//		vinylRecognizer.run();
	}

}
