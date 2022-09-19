package com.produtos.apirest.repository;

import com.produtos.apirest.models.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinarioRepo extends JpaRepository<Veterinario, Long> {
}
