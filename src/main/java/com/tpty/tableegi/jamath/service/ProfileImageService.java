package com.tpty.tableegi.jamath.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class ProfileImageService {

    @Value("${spring.boot.google.profiles}")
    private String PROFILE_DIR;

    @Autowired
    private RestTemplate restTemplate;


    public String downloadAndSaveImage(String imageUrl, String name) throws IOException {

        log.info("Image downloading at this location={}", PROFILE_DIR);
        // Fetch image as byte[]
        byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);

        Path dir = Paths.get(PROFILE_DIR);
        Files.createDirectories(dir); // ensures folder exists

        // Generate unique filename
        String filename = name + ".jpg";
        String filePath = PROFILE_DIR + filename;
        log.info("FilePath={} image Donload location ", filePath);
        // Save to local folder
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBytes);
        }

        // Return relative path to serve later
        return "/profiles/" + filename;
    }

    public byte[] getImageBytes(String imageUrl) throws IOException {

        log.info("Image converting into bytes at this location");
        // Fetch image as byte[]
        return restTemplate.getForObject(imageUrl, byte[].class);
    }

}

