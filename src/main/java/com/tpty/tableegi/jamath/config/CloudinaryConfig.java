package com.tpty.tableegi.jamath.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    @Value("${cloud.google.pics.cloudinary.api.secret}")
    private String CLOUD_GOOGLE_PICS_CLOUDINARY_API_SECRET;

    @Value("${cloud.google.pics.cloudinary.api.key}")
    private String CLOU_GOOGLE_PICS_CLOUDINARY_API_KEY;

    @Value("${cloud.google.pics.cloudinary.name}")
    private String CLOUD_GOOGLE_PICS_CLOUDINARY_NAME;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_GOOGLE_PICS_CLOUDINARY_NAME,
                "api_key", CLOU_GOOGLE_PICS_CLOUDINARY_API_KEY,
                "api_secret", CLOUD_GOOGLE_PICS_CLOUDINARY_API_SECRET));

    }
}
