package com.file.api.fileapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.file.api.fileapi.model.FileResponse;
import com.file.api.fileapi.model.WebResponse;
import com.file.api.fileapi.service.FileStorageService;

@RestController
public class FileStorageController {
    
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public WebResponse<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException{

        FileResponse fileResponse = fileStorageService.uploadFile(file);
        WebResponse<FileResponse> webResponse = WebResponse.<FileResponse>builder()
                .data(fileResponse)
                .build();
        return webResponse;

    }
}
