package com.universidad.repository;

import com.universidad.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    // Verifica si existe una inscripción para un estudiante en una materia
    Boolean existsByEstudiante_IdAndMateria_Id(Long idPersona, Long idMateria);

    // Obtiene todas las inscripciones de un estudiante
    List<Inscripcion> findByEstudiante_Id(Long idPersona);

    // Obtiene todas las inscripciones de una materia
    List<Inscripcion> findByMateria_Id(Long idMateria);

    // Busca una inscripción específica
    Optional<Inscripcion> findByEstudiante_IdAndMateria_Id(Long idPersona, Long idMateria);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Inscripcion> findById(Long idInscripcion);
}