package com.debmeet.banerjee.image_compressor_service.controller;

import com.debmeet.banerjee.image_compressor_service.service.ImageCompressionService;
import com.debmeet.banerjee.image_compressor_service.service.ZipService;
import com.debmeet.banerjee.image_compressor_service.utils.GenericUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/v1/image")
public class ImageUploadController {

    private ImageCompressionService imageCompressionService;

    private ZipService zipService;


    @Autowired
    ImageUploadController(ImageCompressionService imageCompressionService, ZipService zipService) {
        this.imageCompressionService = imageCompressionService;
        this.zipService = zipService;
    }

    @PostMapping("/upload")
    public ResponseEntity<byte[]> imageUpload(@RequestParam("files") MultipartFile[] multipartFiles, @RequestParam("compressionRatio") double compressionRatio) {
        File[] outputFiles = new File[multipartFiles.length];
        imageCompressionService.compressMultipleImages(multipartFiles, compressionRatio, outputFiles);
        File zippedFile = new File (zipService.zipFiles(outputFiles));

        try {
            return ResponseEntity.ok()
                    .headers(GenericUtils.getHttpHeadersForImageDownload(zippedFile.getName()))
                    .body(Files.readAllBytes(zippedFile.toPath()));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null);
        } finally {
            for(File file : outputFiles) {
                file.delete();
            }
            zippedFile.delete();
        }
    }

}
