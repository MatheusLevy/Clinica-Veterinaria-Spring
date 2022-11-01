package com.produtos.apirest.repository;

import com.produtos.apirest.models.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalTypeRepo extends JpaRepository<AnimalType, Long> {
}
