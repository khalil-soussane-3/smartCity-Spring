package com.example.smartcity.repository;

import com.example.smartcity.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    Page<Article> findByNomContains(String kw, Pageable pageable);

}
