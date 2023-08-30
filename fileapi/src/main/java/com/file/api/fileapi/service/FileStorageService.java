package com.file.api.fileapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import com.file.api.fileapi.entity.File;
import com.file.api.fileapi.model.FileResponse;
import com.file.api.fileapi.repository.FileRepository;


@Service
public class FileStorageService {

    @Autowired
    private FileRepository fileRepository;

    public FileResponse toFileResponse(File file) {
        return FileResponse.builder()
            .id(file.getId())
            .nameFile(file.getNameFile())
            .urlFile(file.getUrlFile())
            .size(file.getSize())
            .build();
    }

    // CREATE -------------------------------------------------------------------
    @Transactional
    public FileResponse uploadFile(MultipartFile request) throws IOException {
        String uploadDir = "/media/upload";
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

    private String saveFile(MultipartFile file, String uploadDir, String fileName) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toUri().toString();
        }
    }

}

