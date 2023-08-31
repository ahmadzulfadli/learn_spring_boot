package com.file.api.fileapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponse {
    
    private String id;

    private String nameFile;

    private String urlFile;

    private long size;

}
