package com.produtos.apirest.repository;

import com.produtos.apirest.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {
    Animal findByAnimalId(Long id);
}
