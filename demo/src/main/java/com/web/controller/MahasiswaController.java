package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.model.CreateMahasiswaRequest;
import com.web.model.MahasiswaResponse;
import com.web.model.UpdateMahasiswaRequest;
import com.web.service.MahasiswaService;

@Controller
public class MahasiswaController {

    @Autowired
    private MahasiswaService mahasiswaService;

    // CREATE------------------------------------------------------------------
    @GetMapping("/add")
    public String create(Model model){

        model.addAttribute("request", new CreateMahasiswaRequest());
        return "registrasi";
        
    }

    @PostMapping("/save")
    public String create(CreateMahasiswaRequest request, RedirectAttributes redirectAttributes){
        try{
            mahasiswaService.createMahasiswa(request); 
            redirectAttributes.addFlashAttribute("success", "Success to adding data to database");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("errors", "Field to adding data, please try again");
        }
        return "redirect:/";
        
    }
    // READ--------------------------------------------------------------------
    @GetMapping("/")
    public String getAllData(Model model, @ModelAttribute("success") String successMsg, @ModelAttribute("errors") String errorMsg){

        List<MahasiswaResponse> mahasiswaResponse = mahasiswaService.getAllMahasiswa();
        model.addAttribute("data", mahasiswaResponse);

        //menampilkan pesan
        if(!successMsg.isEmpty()){
            model.addAttribute("succesMsg", successMsg);
            System.out.println(successMsg);
        }
        if(!errorMsg.isEmpty()){
            model.addAttribute("errorMsg", errorMsg);
            System.out.println(errorMsg);
        }

        return "index";
    }
    // UPDATE------------------------------------------------------------------
    @GetMapping("/edit/{id}")
    public String editData(Model model, @PathVariable String id){
            
            MahasiswaResponse mahasiswaResponse = mahasiswaService.getMahasiswaById(id);
            model.addAttribute("data", mahasiswaResponse);
    
            return "edit";
    }
    @PostMapping("/update/{id}")
    public String updateData(@PathVariable String id,UpdateMahasiswaRequest request, RedirectAttributes redirectAttributes){
        try{
            mahasiswaService.updateMahasiswa(id, request); 
            redirectAttributes.addFlashAttribute("success", "Success to update data to database");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("errors", "Field to update data, please try again");
        }
        return "redirect:/";
    }
    // DELETE------------------------------------------------------------------
}
