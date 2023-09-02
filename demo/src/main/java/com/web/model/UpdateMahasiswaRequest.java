package com.web.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateMahasiswaRequest {

    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String email;

    @Size(max = 100)
    private String phone;
}
