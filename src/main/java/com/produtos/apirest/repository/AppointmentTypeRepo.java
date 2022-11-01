package com.produtos.apirest.repository;

import com.produtos.apirest.models.AppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentTypeRepo extends JpaRepository<AppointmentType, Long> {
}
