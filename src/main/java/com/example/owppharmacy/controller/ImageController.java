package com.example.owppharmacy.controller;

import com.example.owppharmacy.service.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Controller
@RequestMapping("/uploads")
public class ImageController {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/images")
    @ResponseBody
    public byte[] getImageWithMediaType(@RequestParam String imageName) throws IOException {
        File file = fileStorageService.load(imageName).getFile();
        byte[] fileContent = Files.readAllBytes(file.toPath());

        InputStream in = new ByteArrayInputStream(fileContent);
        return fileContent;
    }
}
