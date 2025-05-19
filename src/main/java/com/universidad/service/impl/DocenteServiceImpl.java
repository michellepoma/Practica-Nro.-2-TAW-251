package com.universidad.service.impl;

import com.universidad.dto.DocenteDTO;
import com.universidad.model.Docente;
import com.universidad.model.Materia;
import com.universidad.repository.DocenteRepository;
//import com.universidad.repository.EstudianteRepository;
import com.universidad.service.IDocenteService;
import com.universidad.validation.DocenteValidator;
//import com.universidad.validation.EstudianteValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteServiceImpl implements IDocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private DocenteValidator docenteValidator;

    public DocenteServiceImpl(DocenteRepository docenteRepository, DocenteValidator docenteValidator) {
        this.docenteRepository = docenteRepository;
        this.docenteValidator = docenteValidator;
    }

    @Override
    @Cacheable(value = "docentes")
    public List<DocenteDTO> obtenerTodosLosDocentes() {
        return docenteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "docente", key = "#nroEmpleado")
    public DocenteDTO obtenerDocentePorNumeroEmpleado(String nroEmpleado) {
        Docente docente = docenteRepository.findByNroEmpleado(nroEmpleado);
        if (docente == null) {
            throw new RuntimeException("Docente no encontrado con nroEmpleado: " + nroEmpleado);
        }
        return convertToDTO(docente);
    }


    @Override
    public List<Materia> obtenerMateriasDeDocentePorNroEmpleado(String nroEmpleado) {
        Docente docente = docenteRepository.findByNroEmpleado(nroEmpleado);
        if (docente == null) {
            throw new RuntimeException("Docente no encontrado con nroEmpleado: " + nroEmpleado);
        }
        return docente.getMaterias();
    }



    @Override
    @CachePut(value = "docente", key = "#result.nroEmpleado")
    @CacheEvict(value = "docentes", allEntries = true)
    public DocenteDTO crearDocente(DocenteDTO docenteDTO) {
        docenteValidator.validacionCompletaDocente(docenteDTO);

        if (docenteRepository.findByNroEmpleado(docenteDTO.getNroEmpleado()) != null) {
            throw new IllegalArgumentException("El número de empleado ya existe: " + docenteDTO.getNroEmpleado());
        }

        if (docenteDTO.getFechaAlta() == null) {
            docenteDTO.setFechaAlta(LocalDate.now());
        }
        if (docenteDTO.getUsuarioAlta() == null) {
            docenteDTO.setUsuarioAlta("admin"); // O recuperar del contexto de seguridad
        }
        if (docenteDTO.getEstado() == null) {
            docenteDTO.setEstado("activo");
        }

        Docente docente = convertToEntity(docenteDTO);
        Docente guardado = docenteRepository.save(docente);
        return convertToDTO(guardado);
    }

    @Override
    @CachePut(value = "docente", key = "#id")
    @CacheEvict(value = "docentes", allEntries = true)
    public DocenteDTO actualizarDocente(Long id, DocenteDTO docenteDTO) {
        Docente existente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        existente.setNombre(docenteDTO.getNombre());
        existente.setApellido(docenteDTO.getApellido());
        existente.setEmail(docenteDTO.getEmail());
        existente.setFechaNacimiento(docenteDTO.getFechaNacimiento());
        existente.setNroEmpleado(docenteDTO.getNroEmpleado());
        existente.setDepartamento(docenteDTO.getDepartamento());
        existente.setUsuarioModificacion("admin");
        existente.setFechaModificacion(LocalDate.now());

        Docente actualizado = docenteRepository.save(existente);
        return convertToDTO(actualizado);
    }

    @Override
    @CacheEvict(value = {"docente", "docentes", "materiasDocente"}, allEntries = true)
    public DocenteDTO eliminarDocente(Long id, DocenteDTO docenteDTO) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        docente.setEstado("inactivo");
        docente.setUsuarioBaja("admin");
        docente.setFechaBaja(LocalDate.now());
        docente.setMotivoBaja(docenteDTO.getMotivoBaja());

        Docente inactivado = docenteRepository.save(docente);
        return convertToDTO(inactivado);
    }

    // Métodos auxiliares para conversión

    private DocenteDTO convertToDTO(Docente docente) {
        return DocenteDTO.builder()
                .id(docente.getId())
                .nombre(docente.getNombre())
                .apellido(docente.getApellido())
                .email(docente.getEmail())
                .fechaNacimiento(docente.getFechaNacimiento())
                .nroEmpleado(docente.getNroEmpleado())
                .departamento(docente.getDepartamento())
                .estado(docente.getEstado())
                .usuarioAlta(docente.getUsuarioAlta())
                .fechaAlta(docente.getFechaAlta())
                .usuarioModificacion(docente.getUsuarioModificacion())
                .fechaModificacion(docente.getFechaModificacion())
                .usuarioBaja(docente.getUsuarioBaja())
                .fechaBaja(docente.getFechaBaja())
                .motivoBaja(docente.getMotivoBaja())
                .build();
    }

    private Docente convertToEntity(DocenteDTO dto) {
        return Docente.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .fechaNacimiento(dto.getFechaNacimiento())
                .nroEmpleado(dto.getNroEmpleado())
                .departamento(dto.getDepartamento())
                .estado(dto.getEstado())
                .usuarioAlta(dto.getUsuarioAlta())
                .fechaAlta(dto.getFechaAlta())
                .usuarioModificacion(dto.getUsuarioModificacion())
                .fechaModificacion(dto.getFechaModificacion())
                .usuarioBaja(dto.getUsuarioBaja())
                .fechaBaja(dto.getFechaBaja())
                .motivoBaja(dto.getMotivoBaja())
                .build();
    }
}
