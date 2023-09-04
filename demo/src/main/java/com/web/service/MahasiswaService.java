package com.web.service;

import java.util.List;
import java.util.UUID;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.web.entity.Mahasiswa;
import com.web.model.CreateMahasiswaRequest;
import com.web.model.MahasiswaResponse;
import com.web.model.UpdateMahasiswaRequest;
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
    public MahasiswaResponse getMahasiswaById(String id){
        Mahasiswa mahasiswa = mahasiswaRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        return toMahasiswaResponse(mahasiswa);
    }

    @Transactional(readOnly = true)
    public List<MahasiswaResponse> getAllMahasiswa(){
        List<Mahasiswa> allData = mahasiswaRepository.findAll();
        return allData.stream().map(this::toMahasiswaResponse).toList();
    }
    // UPDATE------------------------------------------------------------------
    @Transactional
    public MahasiswaResponse updateMahasiswa(String id, UpdateMahasiswaRequest request){

        validationService.validate(request);

        Mahasiswa updateMahasiswa = mahasiswaRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        updateMahasiswa.setName(request.getName());
        updateMahasiswa.setEmail(request.getEmail());
        updateMahasiswa.setPhone(request.getPhone());
        mahasiswaRepository.save(updateMahasiswa);

        return toMahasiswaResponse(updateMahasiswa);
    }

    // DELETE------------------------------------------------------------------
    @Transactional
    public void deleteMahasiswa(String id){
        Mahasiswa deleteMahasiswa = mahasiswaRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        mahasiswaRepository.delete(deleteMahasiswa);
    }
}
