package com.example.smartcity.repository;

import com.example.smartcity.entity.Etablissement;
import com.example.smartcity.entity.TypeEtablissement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeEtablissementRepository extends JpaRepository<TypeEtablissement,Long> {
    Page<TypeEtablissement> findByNomContains(String kw, Pageable pageable);

}
