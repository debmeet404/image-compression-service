package com.debmeet.banerjee.image_compressor_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class ImageCompressorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageCompressorServiceApplication.class, args);
	}

}
