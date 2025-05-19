package com.universidad.repository;

import com.universidad.model.Docente;

import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Importa la anotación Repository de Spring

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByNroEmpleado(String nroEmpleado);
    Docente findByNroEmpleado(String nroEmpleado);
    List<Docente> findByNroEmpleadoIn(List<String> nroEmpleados);
}

