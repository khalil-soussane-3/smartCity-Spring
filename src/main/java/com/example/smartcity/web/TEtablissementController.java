package com.example.smartcity.web;

import com.example.smartcity.entity.Etablissement;
import com.example.smartcity.entity.TypeEtablissement;
import com.example.smartcity.repository.EtablissementRepository;
import com.example.smartcity.repository.TypeEtablissementRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class TEtablissementController {
    private TypeEtablissementRepository typeEtablissementRepository;
    private EtablissementRepository etablissementRepository;


    @GetMapping(path="/user/indexType")
    public String typeEtablissement(Model model
            , @RequestParam(name = "page" , defaultValue = "0") int page
            , @RequestParam(name = "size" , defaultValue = "5") int size
            , @RequestParam(name = "keyword",defaultValue = "") String keyword){
        Page<TypeEtablissement> typeEtablissementPage=typeEtablissementRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listType",typeEtablissementPage.getContent());
        model.addAttribute("pages",new int[typeEtablissementPage.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "typeEtablissement";}

    @GetMapping("/admin/deleteType")
    public String delete(Long id,int page,String keyword){
        typeEtablissementRepository.deleteById(id);
        return "redirect:/user/indexType?page="+page+"&keyword="+keyword;
    }


    @GetMapping("/admin/formType")
    public String formPatient(Model model){
        model.addAttribute("type",new TypeEtablissement());
        return "formType";
    }

    @PostMapping("/admin/saveTypeEtablissement")
    public String save(Model model , @Valid TypeEtablissement typeEtablissement, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "")String keyword){
        if(bindingResult.hasErrors()) {
            return "formType";
        }
        typeEtablissementRepository.save(typeEtablissement);
        return "redirect:/user/indexType?page="+page+"&keyword="+keyword;
    }

}
