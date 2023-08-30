package com.file.api.fileapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.file.api.fileapi.model.FileResponse;
import com.file.api.fileapi.model.WebResponse;
import com.file.api.fileapi.service.FileStorageService;

@RestController
public class FileStorageController {
    
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(
        path = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<FileResponse>> uploadFile(@RequestPart("file") MultipartFile file) {
        try {
            FileResponse response = fileStorageService.uploadFile(file);
            WebResponse<FileResponse> webResponse = WebResponse.<FileResponse>builder()
                    .data(response)
                    .build();
            return ResponseEntity.ok(webResponse);
        } catch (IOException e) {
            WebResponse<FileResponse> errorResponse = WebResponse.<FileResponse>builder()
                    .error("An error occurred during file upload.")
                    .build();
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

}
