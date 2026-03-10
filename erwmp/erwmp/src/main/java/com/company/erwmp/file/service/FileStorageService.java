package com.company.erwmp.file.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {   //Hiring a permanent warehouse manager who handles all file storage tasks.

    @Value("${file.upload-dir}")  //Reads value from application.properties or application.yml
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {    //Accepts a file uploaded via HTTP request

        String originalName = file.getOriginalFilename();   //Retrieves the filename sent by the client
        String extension = originalName.substring(originalName.lastIndexOf("."));  //Finds the last dot (.)Extracts .pdf, .jpg, etc.
        String storedName = UUID.randomUUID() + extension;  //Generates a globally unique identifier

        Path targetPath = Paths.get(uploadDir).resolve(storedName);  //Combines upload directory + file name and Produces a full file path
        Files.copy(file.getInputStream(), targetPath);   //Reads file content from memory

        return storedName;
    }
}
/*

FileStorageService handles how and where the file is stored.

🏭 FileStorageService = Warehouse Worker
What the warehouse does:
Physically stores the package
Chooses shelf/location
Retrieves or deletes packages

📌 In your app, FileStorageService:

Saves file to disk / S3 / cloud
Generates file paths
Reads file back
Deletes physical files

💬 “I’ll put the file on shelf A-3 and give you the location.”


* 1. User clicks Upload
2. HTTP request is sent
3. Backend receives MultipartFile
4. Backend saves file to disk  ✅ (IMMEDIATE)
5. Backend returns response (filename / id)
6. UI receives response
7. UI decides what to do with it
*
* */