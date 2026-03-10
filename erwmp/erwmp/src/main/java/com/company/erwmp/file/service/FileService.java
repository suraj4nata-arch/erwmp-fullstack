package com.company.erwmp.file.service;

import com.company.erwmp.file.entity.FileEntity;
import com.company.erwmp.file.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    private final FileRepository fileRepository;   //talks to the DB
    private final FileStorageService storageService;   //talks to the fileSystem

    public FileService(FileRepository fileRepository, FileStorageService storageService) {
        this.fileRepository = fileRepository;
        this.storageService = storageService;
    }

    public FileEntity upload(MultipartFile file) throws IOException {   //Accepts uploaded file from HTTP request

        String storedFileName = storageService.storeFile(file);   //Stores the file

        FileEntity entity = new FileEntity();  //Creating an object, like Opening a new page in the document register
        entity.setOriginalFileName(file.getOriginalFilename());    //Writing the document’s original title in the register
        entity.setStoredFileName(storedFileName);  //Writing the internal archive code
        entity.setContentType(file.getContentType()); //type of file
        entity.setSize(file.getSize()); //size of file
        entity.setFilePath(storedFileName);   //Currently same as storedFileName, later it can become random_numbers.png

        return fileRepository.save(entity);  //save the file
    }
}

/*
*
* 🧠 Big Picture (What this service does)

This service:

* Accepts an uploaded file
* Saves the actual file to disk
* Creates a database record for that file
* Returns the saved metadata
*
*
* 🧠 FileService = Office Manager

What the manager does:
Checks if the package is allowed
Decides where it should go
Records details in the system
Talks to different teams

📌 In your app, FileService:

Validates file type/size
Applies business rules
Saves file metadata in DB
Calls FileStorageService

💬 “Yes, store this file, and remember its details.”
* */