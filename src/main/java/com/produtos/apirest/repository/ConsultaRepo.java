package com.produtos.apirest.repository;

import com.produtos.apirest.models.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepo extends JpaRepository<Consulta, Long> {
}
