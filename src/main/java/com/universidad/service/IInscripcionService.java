package com.universidad.service;

import com.universidad.dto.InscripcionDTO;
import java.util.List;

public interface IInscripcionService {

    /**
     * Obtiene todas las inscripciones.
     * @return Lista de InscripcionDTO.
     */
    List<InscripcionDTO> obtenerTodasLasInscripciones();

    /**
     * Obtiene las inscripciones de un estudiante.
     * @param idEstudiante ID del estudiante.
     * @return Lista de InscripcionDTO.
     */
    List<InscripcionDTO> obtenerInscripcionesPorEstudiante(Long idEstudiante);

    /**
     * Obtiene las inscripciones de una materia.
     * @param idMateria ID de la materia.
     * @return Lista de InscripcionDTO.
     */
    List<InscripcionDTO> obtenerInscripcionesPorMateria(Long idMateria);

    /**
     * Obtiene una inscripción por su ID.
     * @param id ID de la inscripción.
     * @return InscripcionDTO.
     */
    InscripcionDTO obtenerInscripcionPorId(Long id);

    /**
     * Crea una nueva inscripción.
     * @param inscripcionDTO DTO de la inscripción a crear.
     * @return InscripcionDTO creada.
     */
    InscripcionDTO crearInscripcion(InscripcionDTO inscripcionDTO);

    /**
     * Actualiza una inscripción existente.
     * @param id ID de la inscripción.
     * @param inscripcionDTO DTO con los nuevos datos.
     * @return InscripcionDTO actualizada.
     */
    InscripcionDTO actualizarInscripcion(Long id, InscripcionDTO inscripcionDTO);

    /**
     * Realiza la baja lógica de una inscripción (abandono).
     * @param id ID de la inscripción a dar de baja.
     * @param inscripcionDTO DTO con motivo de baja, usuarioBaja, etc.
     * @return InscripcionDTO actualizada con estado "abandonada".
     */
    InscripcionDTO abandonarInscripcion(Long id, InscripcionDTO inscripcionDTO);
}