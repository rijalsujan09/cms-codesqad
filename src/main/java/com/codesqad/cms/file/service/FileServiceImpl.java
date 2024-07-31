package com.codesqad.cms.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();

        String randomUUID = UUID.randomUUID().toString();
        String fileName1 = randomUUID.concat(name.substring(name.lastIndexOf(".")));

        String filePath = path + File.separator + fileName1;

        File directory = new File(filePath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IOException("Failed to create directory: " + path);
            }
        }
        try {
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            return fileName1;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed to upload file: " + e.getMessage());
        }
    }

    @Override
    public InputStream getResource(String path, String fileName) throws IOException {
        String fullPath = path + File.separator + fileName;
        return Files.newInputStream(Paths.get(fullPath));
    }


    public void deleteFile(String path, String fileName) throws IOException {
        String fullPath = path + File.separator + fileName;
        Path filePath = Paths.get(fullPath);

        if (Files.exists(filePath)) {
            Files.deleteIfExists(filePath);
        } else {
            throw new IOException("File does not exist: " + fullPath);
        }
    }
}
