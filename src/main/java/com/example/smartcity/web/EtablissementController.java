package com.example.smartcity.web;

import com.example.smartcity.entity.Article;
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
public class EtablissementController {

    private EtablissementRepository etablissementRepository;
    private TypeEtablissementRepository typeEtablissementRepository;

    @GetMapping(path="/user/indexEtablissement")
    public String etablissements(Model model
            , @RequestParam(name = "page" , defaultValue = "0") int page
            , @RequestParam(name = "size" , defaultValue = "5") int size
            , @RequestParam(name = "keyword",defaultValue = "") String keyword){
        Page<Etablissement> etablissementPage=etablissementRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listEtablissement",etablissementPage.getContent());
        model.addAttribute("pages",new int[etablissementPage.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "Etablissements";}

    @GetMapping("/admin/deleteEtablissement")
    public String delete(Long id,int page,String keyword){
        etablissementRepository.deleteById(id);
        return "redirect:/user/indexEtablissement?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/indexEtablissement";
    }

    @GetMapping("/admin/formEtablissements")
    public String form(Model model,Long id){
        TypeEtablissement typeEtablissement =typeEtablissementRepository.findById(id).orElse(null);
        Etablissement etablissement = new Etablissement();
        etablissement.setTypeEtablissement(typeEtablissement);
        model.addAttribute("etablissement",etablissement);
        return "formEtablissements";
    }

    @PostMapping("/admin/saveEtablissement")
    public String save(Model model , @Valid Etablissement etablissement, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "")String keyword){
        if(bindingResult.hasErrors()) {
            model.addAttribute("types", typeEtablissementRepository.findAll());
            return "formEtablissements";
        }
        etablissementRepository.save(etablissement);
        return "redirect:/indexEtablissement?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/editEtablissement")
    public String edit(Model model,Long id,String keyword, int page){
        Etablissement etablissement = etablissementRepository.findById(id).orElse(null);
        if (etablissement==null) throw new RuntimeException("Etablissement introuvable") ;
        List<TypeEtablissement> typeEtablissements = typeEtablissementRepository.findAll();
        model.addAttribute("etablissement",etablissement);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "editEtablissement";
    }

}
