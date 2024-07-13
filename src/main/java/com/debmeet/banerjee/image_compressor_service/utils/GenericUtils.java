package com.debmeet.banerjee.image_compressor_service.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class GenericUtils {

    public static HttpHeaders getHttpHeadersForImageDownload(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return headers;
    }

}
