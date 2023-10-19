package com.example.smartcity.repository;

import com.example.smartcity.entity.Etablissement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement,Long>{
    Page<Etablissement> findByNomContains(String kw, Pageable pageable);

}
