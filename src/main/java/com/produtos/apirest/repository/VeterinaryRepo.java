package com.produtos.apirest.repository;

import com.produtos.apirest.models.Veterinary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinaryRepo extends JpaRepository<Veterinary, Long> {
}
