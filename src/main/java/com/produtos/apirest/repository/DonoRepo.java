package com.produtos.apirest.repository;

import com.produtos.apirest.models.Dono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonoRepo extends JpaRepository<Dono, Long> {
}
