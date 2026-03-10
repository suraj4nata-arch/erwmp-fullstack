package com.company.erwmp.file.controller;

import com.company.erwmp.file.entity.FileEntity;
import com.company.erwmp.file.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "File is empty"));
        }

        System.out.println("Received file: " + file.getOriginalFilename());

        // Store the file and get its path
        String filePath = String.valueOf(fileService.upload(file));

        // Return JSON response
        Map<String, String> response = Map.of(
                "fileName", file.getOriginalFilename(),
                "filePath", filePath,
                "message", "File uploaded successfully"
        );


        return ResponseEntity.ok(response);
    }

}
