package com.file.api.fileapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    // CREATE -------------------------------------------------------------------
    @PostMapping("/upload")
    public WebResponse<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException{

        FileResponse fileResponse = fileStorageService.uploadFile(file);
        WebResponse<FileResponse> webResponse = WebResponse.<FileResponse>builder()
                .data(fileResponse)
                .build();
        return webResponse;

    }

    // READ -------------------------------------------------------------------
    @GetMapping("/data/{id}")
    public WebResponse<FileResponse> getFile(@PathVariable String id){
        FileResponse fileResponse = fileStorageService.getFile(id);

        WebResponse<FileResponse> webResponse = WebResponse.<FileResponse>builder()
                    .data(fileResponse)
                    .build();
        return webResponse;
    }
    @GetMapping("/data")
    public WebResponse<List<FileResponse>> getAllData(){
        List<FileResponse> fileResponse = fileStorageService.getAllData();

        WebResponse<List<FileResponse>> webResponse = WebResponse.<List<FileResponse>>builder()
                    .data(fileResponse)
                    .build();
        return webResponse;
    }

    // UPDATE -------------------------------------------------------------------
    @PatchMapping("/update/{id}")
    public WebResponse<FileResponse> updateFile(@PathVariable String id,@RequestParam("file") MultipartFile file) throws IOException{

        FileResponse fileResponse = fileStorageService.updateFile(id, file);
        WebResponse<FileResponse> webResponse = WebResponse.<FileResponse>builder()
                .data(fileResponse)
                .build();
        return webResponse;

    }
    // DELETE -------------------------------------------------------------------
    @DeleteMapping("/data/{id}")
    public WebResponse<String> deleteData(@PathVariable String id) throws IOException{
        fileStorageService.deleteData(id);

        WebResponse<String> webResponse = WebResponse.<String>builder()
                    .data("Sukses deleted data id : " + id)
                    .build();
        return webResponse;
    }

}