package com.produtos.apirest.repository;

import com.produtos.apirest.models.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertiseRepo extends JpaRepository<Expertise, Long> {
    Expertise findByExpertiseId(Long id);
}
