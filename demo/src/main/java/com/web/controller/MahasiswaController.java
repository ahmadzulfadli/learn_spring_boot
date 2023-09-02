package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.model.CreateMahasiswaRequest;
import com.web.model.MahasiswaResponse;
import com.web.service.MahasiswaService;

@Controller
@RequestMapping("")
public class MahasiswaController {

    @Autowired
    private MahasiswaService mahasiswaService;
    
    // @GetMapping
    // public String welcome(Model model){
    //     String msg= "hallo fadli";
    //     model.addAttribute("msg",msg);
    //     return "index";
    // }

    // CREATE------------------------------------------------------------------
    @GetMapping("/mahasiswa/data/add")
    public String create(Model model){
        return "registrasi";
    }
    // READ--------------------------------------------------------------------
    @GetMapping("")
    public String getAllData(Model model){
        List<MahasiswaResponse> mahasiswaResponse = mahasiswaService.getAllMahasiswa();
        model.addAttribute("data", mahasiswaResponse);
        return "index";
    }

    // UPDATE------------------------------------------------------------------
    // DELETE------------------------------------------------------------------
}
