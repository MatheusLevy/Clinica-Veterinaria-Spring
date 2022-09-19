package com.produtos.apirest.repository;

import com.produtos.apirest.models.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepo extends JpaRepository<Area, Long> {

}
