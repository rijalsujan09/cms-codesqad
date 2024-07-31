package com.codesqad.cms.file.controller;

import com.codesqad.cms.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/file")
public class FileController {

    @Value("${project.image:/images}")
    private String uploadPath;

    @Autowired
    private FileService fileService;

    @PostMapping("/image")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        try {
            String fileName = fileService.uploadImage(uploadPath, file);
            model.addAttribute("message", "File uploaded successfully: " + fileName);
            try {
                List<String> imageFiles = Files.list(Paths.get(uploadPath))
                        .map(path -> path.getFileName().toString())
                        .collect(Collectors.toList());
                model.addAttribute("images", imageFiles);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "File upload failed: " + e.getMessage());
        }
        return "file/image-list";
    }

    @GetMapping("/upload")
    public String listImages(Model model) {
        try {
            List<String> imageFiles = Files.list(Paths.get(uploadPath))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            model.addAttribute("images", imageFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "file/image-upload";
    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<byte[]> viewImage(@PathVariable String fileName) {
        try {
            byte[] image = Files.readAllBytes(Paths.get(uploadPath, fileName));
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable String fileName) {
        try {
            InputStream inputStream = fileService.getResource(uploadPath, fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public String deleteImage(@PathVariable String fileName, Model model) {
        try {
            Files.delete(Paths.get(uploadPath, fileName));
            List<String> imageFiles = Files.list(Paths.get(uploadPath))
                   .map(path -> path.getFileName().toString())
                   .collect(Collectors.toList());
            model.addAttribute("images", imageFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "file/image-list";
    }
}
