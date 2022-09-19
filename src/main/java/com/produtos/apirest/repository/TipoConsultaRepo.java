package com.produtos.apirest.repository;

import com.produtos.apirest.models.TipoConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoConsultaRepo extends JpaRepository<TipoConsulta, Long> {
}
