package com.universidad.service;

import com.universidad.dto.DocenteDTO;
import com.universidad.model.Materia;

import java.util.List;

public interface IDocenteService {

    /**
     * Obtiene todos los docentes.
     * @return Lista de DocenteDTO.
     */
    List<DocenteDTO> obtenerTodosLosDocentes();

    /**
     * Obtiene un docente por su número de empleado.
     * @param nroEmpleado Número de empleado.
     * @return DocenteDTO.
     */
    DocenteDTO obtenerDocentePorNumeroEmpleado(String nroEmpleado);

    /**
     * Obtiene las materias asignadas a un docente por su ID.
     * @param nroEmpleado ID del docente.
     * @return Lista de materias.
     */
    List<Materia> obtenerMateriasDeDocentePorNroEmpleado(String nroEmpleado);

    /**
     * Crea un nuevo docente.
     * @param docenteDTO Datos del docente.
     * @return DocenteDTO creado.
     */
    DocenteDTO crearDocente(DocenteDTO docenteDTO);

    /**
     * Actualiza los datos de un docente.
     * @param id ID del docente.
     * @param docenteDTO Datos actualizados.
     * @return DocenteDTO actualizado.
     */
    DocenteDTO actualizarDocente(Long id, DocenteDTO docenteDTO);

    /**
     * Realiza la baja lógica de un docente.
     * @param id ID del docente.
     * @param docenteDTO Datos con motivo de baja, usuarioBaja, etc.
     * @return DocenteDTO actualizado con estado "inactivo".
     */
    DocenteDTO eliminarDocente(Long id, DocenteDTO docenteDTO);
}