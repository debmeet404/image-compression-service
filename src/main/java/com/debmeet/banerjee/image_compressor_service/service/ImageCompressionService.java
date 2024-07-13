package com.debmeet.banerjee.image_compressor_service.service;

import org.springframework.stereotype.Service;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.debmeet.banerjee.image_compressor_service.utils.Constants.*;

@Service
public class ImageCompressionService {


    private void compressImage(File file, double quality, File outputFile, int width, int height) {
        try {
            Thumbnails.of(file)
                    .size(width, height)
                    .outputQuality(quality)
                    .toFile(outputFile);
        } catch (IllegalStateException | IOException e) {
            System.out.println("Exception Occurred in Image Compression " + e);
        }
    }

    public void compressMultipleImages(MultipartFile[] multipartFiles, double quality, File[] outputFiles) {
        for (int i = 0; i < multipartFiles.length; i++) {
            File inputFile = new File(INPUT_PREFIX + multipartFiles[i].getOriginalFilename());
            File outputFile = new File(OUTPUT_PREFIX + multipartFiles[i].getOriginalFilename());
            BufferedImage originalImage = null;
            int originalWidth = FALLBACK_WIDTH;
            int originalHeight = FALLBACK_HEIGHT;
            try {
                multipartFiles[i].transferTo(inputFile.toPath());
                originalImage = ImageIO.read(inputFile);
            } catch (IOException | IllegalStateException e) {
                System.out.println("Exception Occurred in MultiPartFile To File Conversion " + e);
            }

            if (Objects.nonNull(originalImage)) {
                originalWidth = originalImage.getWidth();
                originalHeight = originalImage.getHeight();
            }

            compressImage(inputFile, quality, outputFile, originalWidth, originalHeight);
            outputFiles[i] = outputFile;
            inputFile.delete();
        }
    }

}
