package com.file.api.fileapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.file.api.fileapi.entity.File;
import com.file.api.fileapi.model.FileResponse;
import com.file.api.fileapi.repository.FileRepository;


@Service
public class FileStorageService {

    @Autowired
    private FileRepository fileRepository;

    // Fungsi Tambahan
    private FileResponse toFileResponse(File file) {
        return FileResponse.builder()
            .id(file.getId())
            .nameFile(file.getNameFile())
            .urlFile(file.getUrlFile())
            .size(file.getSize())
            .build();
    }

    private String saveFile(MultipartFile file, String uploadDir, String fileName) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path path = uploadPath.resolve(fileName);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            URI uri = URI.create(path.toString());
            String filePath = uri.getPath();
            return filePath;
        }
    }

    private void deleteFile(String path) throws IOException{
        Path filePath = Paths.get(path);
        if (Files.exists(filePath)){
            Files.delete(filePath);
        }else{
            throw new FileNotFoundException("File not found at path" + path);
        }
    }


    // CREATE -------------------------------------------------------------------
    @Transactional
    public FileResponse uploadFile(MultipartFile request) throws IOException {
        String uploadDir = "/home/pr001/Desktop/SpringBoot/upload";
        String fileName = StringUtils.cleanPath(request.getOriginalFilename());
        long size = request.getSize();
        String fileUrl = saveFile(request, uploadDir, fileName);

        File file = new File();
        file.setId(UUID.randomUUID().toString());
        file.setNameFile(fileName);
        file.setUrlFile(fileUrl);
        file.setSize(size);

        fileRepository.save(file);

        return toFileResponse(file);
    }

    // READ -------------------------------------------------------------------
    @Transactional(readOnly = true)
    public FileResponse getFile(String id){

        File file = fileRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Data not found"));

        return toFileResponse(file);
    }

    @Transactional(readOnly = true)
    public List<FileResponse> getAllData(){
        List<File> file = fileRepository.findAll();
        return file.stream().map(this::toFileResponse).toList();
    }

    // UPDATE -------------------------------------------------------------------
    @Transactional
    public FileResponse updateFile(String fileId, MultipartFile request) throws IOException {
        Optional<File> optionalFile = fileRepository.findById(fileId);
        
        if (optionalFile.isPresent()) {
            File file = optionalFile.get();

            deleteFile(file.getUrlFile());
            
            String uploadDir = "/home/pr001/Desktop/SpringBoot/upload";
            String fileName = StringUtils.cleanPath(request.getOriginalFilename());
            long size = request.getSize();
            String fileUrl = saveFile(request, uploadDir, fileName);

            // Update file properties
            file.setNameFile(fileName);
            file.setUrlFile(fileUrl);
            file.setSize(size);

            fileRepository.save(file);

            return toFileResponse(file);
        } else {
            throw new FileNotFoundException("File not found with ID: " + fileId);
        }
    }

    // DELETE -------------------------------------------------------------------
    @Transactional
    public void deleteData(String id) throws IOException{
        File file = fileRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        deleteFile(file.getUrlFile());
        fileRepository.delete(file);
    }
}

