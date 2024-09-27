package com.lab08.main.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lab08.main.service.UploadService;

import jakarta.servlet.ServletContext;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    ServletContext app;

    @Override
    public File save(MultipartFile file, String folder) {
        // Lưu vào thư mục của ứng dụng
        File appDir = new File(app.getRealPath("/assets/" + folder));
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        // Lưu vào đường dẫn cụ thể
        Path specificPath = Paths.get("G:/Nghiencuu/Assignment/src/main/resources/static/assets/" + folder);
        if (!Files.exists(specificPath)) {
            try {
                Files.createDirectories(specificPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create specific directory", e);
            }
        }

        String s = System.currentTimeMillis() + file.getOriginalFilename();
        String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));

        try {
            // Lưu vào thư mục của ứng dụng
            File savedFile = new File(appDir, name);
            file.transferTo(savedFile);
            System.out.println("Saved to app directory: " + savedFile.getAbsolutePath());

            // Lưu vào đường dẫn cụ thể
            Path specificFilePath = specificPath.resolve(name);
            Files.copy(savedFile.toPath(), specificFilePath);
            System.out.println("Saved to specific directory: " + specificFilePath.toAbsolutePath());

            return savedFile;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }
}