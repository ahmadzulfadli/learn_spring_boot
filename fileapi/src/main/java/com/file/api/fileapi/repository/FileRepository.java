package com.file.api.fileapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.file.api.fileapi.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, String>{
    
}
