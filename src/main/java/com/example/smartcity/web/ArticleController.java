package com.example.smartcity.web;

import com.example.smartcity.entity.Article;
import com.example.smartcity.entity.Etablissement;
import com.example.smartcity.repository.ArticleRepository;
import com.example.smartcity.repository.EtablissementRepository;
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

@Controller
@AllArgsConstructor
public class ArticleController {
    private ArticleRepository articleRepository;

    private EtablissementRepository etablissementRepository ;

    @GetMapping(path="/user/indexArticle")
    public String aricles(Model model
            , @RequestParam(name = "page" , defaultValue = "0") int page
            , @RequestParam(name = "size" , defaultValue = "5") int size
            , @RequestParam(name = "keyword",defaultValue = "") String keyword){
        Page<Article> articles=articleRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listArticles",articles.getContent());
        model.addAttribute("pages",new int[articles.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "Articles";}

    @GetMapping("/admin/deleteArticles")
    public String delete(Long id,int page,String keyword){
        articleRepository.deleteById(id);
        return "redirect:/user/indexArticle?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formArticle")
    public String form(Model model,Long id){
        Etablissement etablissement = etablissementRepository.findById(id).orElse(null);
        Article article = new Article() ;
        article.setEtablissement(etablissement);
        model.addAttribute("article",article);
        return "formArticle";
    }

    @PostMapping("/admin/saveArticle")
    public String save(Model model , @Valid Article article, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "")String keyword){
        if(bindingResult.hasErrors()) {
            model.addAttribute("types", etablissementRepository.findAll());
            return "formEtablissements";
        }
        articleRepository.save(article);
        return "redirect:/user/indexArticle?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/editArticle")
    public String edit(Model model,Long id,String keyword, int page){
        Article article = articleRepository.findById(id).orElse(null);
        if (article==null) throw new RuntimeException("Article introuvable") ;
        model.addAttribute("article",article);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "editArticle";
    }
}
