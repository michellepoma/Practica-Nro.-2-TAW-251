package com.universidad.validation;

import com.universidad.dto.InscripcionDTO;
import com.universidad.repository.InscripcionRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InscripcionValidator {

    private final InscripcionRepository inscripcionRepository;

    public InscripcionValidator(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    public void validarDuplicada(Long idEstudiante, Long idMateria) {
        if (inscripcionRepository.existsByEstudiante_IdAndMateria_Id(idEstudiante, idMateria)) {
            throw new IllegalArgumentException("El estudiante ya está inscrito en esta materia.");
        }
    }

    public void validarEstadoPermitido(String estado) {
        List<String> estadosValidos = Arrays.asList("activa", "abandonada");
        if (!estadosValidos.contains(estado.toLowerCase())) {
            throw new IllegalArgumentException("Estado no permitido. Debe ser 'activa' o 'abandonada'.");
        }
    }

    public void validarCompletaInscripcion(InscripcionDTO dto) {
        validarEstadoPermitido(dto.getEstado());
        validarDuplicada(dto.getIdEstudiante(), dto.getIdMateria());
        // Puedes agregar más validaciones si es necesario
    }
}