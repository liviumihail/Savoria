package com.example.demo.controller;
import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadImageController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/upload-image")
    public String showImageForm(Model model) {
        byte[] imageData = getImageData();
        if (imageData != null) {
            model.addAttribute("imageData", Base64.getEncoder().encodeToString(imageData));
        }
        return "upload-image";
    }

    @PostMapping("/")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) {
        try {
            byte[] data = file.getBytes();
            saveImage(data);
            model.addAttribute("imageData", Base64.getEncoder().encodeToString(data));
            return "upload-image";
        } catch (IOException e) {
            return "error-page";
        }
    }

    @GetMapping("/display-image")
    public ResponseEntity<byte[]> displayImage() {
        byte[] imageData = getImageData();
        if (imageData != null) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void saveImage(byte[] data) {
        String sql = "UPDATE products SET image = ? WHERE id = 1";
        jdbcTemplate.update(sql, data);
    }

    private byte[] getImageData() {
        String sql = "SELECT image FROM products ORDER BY id DESC LIMIT 1"; //LIMIT 1
        return jdbcTemplate.queryForObject(sql, byte[].class);
    }


}
