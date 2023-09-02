package com.web.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.entity.Mahasiswa;
import com.web.model.CreateMahasiswaRequest;
import com.web.model.MahasiswaResponse;
import com.web.repository.MahasiswaRepository;

@Service
public class MahasiswaService {
    
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private ValidationService validationService;

    private MahasiswaResponse toMahasiswaResponse(Mahasiswa mahasiswa){
        return MahasiswaResponse.builder()
        .id(mahasiswa.getId())
        .name(mahasiswa.getName())
        .email(mahasiswa.getEmail())
        .phone(mahasiswa.getPhone())
        .build();
    }

    // CREATE------------------------------------------------------------------
    @Transactional
    public MahasiswaResponse createMahasiswa(CreateMahasiswaRequest request){

        validationService.validate(request);

        Mahasiswa addMahasiswa = new Mahasiswa();

        addMahasiswa.setId(UUID.randomUUID().toString());
        addMahasiswa.setName(request.getName());
        addMahasiswa.setEmail(request.getEmail());
        addMahasiswa.setPhone(request.getPhone());
        mahasiswaRepository.save(addMahasiswa);

        return toMahasiswaResponse(addMahasiswa);
    }
    // READ--------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<MahasiswaResponse> getAllMahasiswa(){
        List<Mahasiswa> allData = mahasiswaRepository.findAll();
        return allData.stream().map(this::toMahasiswaResponse).toList();
    }
    // UPDATE------------------------------------------------------------------
    // DELETE------------------------------------------------------------------
}
