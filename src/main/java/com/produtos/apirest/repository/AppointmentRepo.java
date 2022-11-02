package com.produtos.apirest.repository;

import com.produtos.apirest.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    Appointment findByAppointmentId(Long id);
}
