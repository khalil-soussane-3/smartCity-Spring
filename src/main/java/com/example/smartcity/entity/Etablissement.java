package com.example.smartcity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Etablissement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom ;
    private String adresse ;
    private String numTel ;
    @JsonIgnore
    @OneToMany(mappedBy = "etablissement")
    private List<Article> articles ;

    @ManyToOne
    @JoinColumn(name = "id_type")
    private TypeEtablissement typeEtablissement ;
}
