package com.universidad.service.impl;

import com.universidad.dto.InscripcionDTO;
import com.universidad.model.Estudiante;
import com.universidad.model.Inscripcion;
import com.universidad.model.Materia;
import com.universidad.repository.EstudianteRepository;
import com.universidad.repository.InscripcionRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IInscripcionService;
import com.universidad.validation.InscripcionValidator;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionServiceImpl implements IInscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private InscripcionValidator inscripcionValidator;

    @Override
    @Cacheable(value = "inscripciones")
    public List<InscripcionDTO> obtenerTodasLasInscripciones() {
        return inscripcionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "inscripcionesEstudiante", key = "#idEstudiante")
    public List<InscripcionDTO> obtenerInscripcionesPorEstudiante(Long idEstudiante) {
        return inscripcionRepository.findByEstudiante_Id(idEstudiante).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "inscripcionesMateria", key = "#idMateria")
    public List<InscripcionDTO> obtenerInscripcionesPorMateria(Long idMateria) {
        return inscripcionRepository.findByMateria_Id(idMateria).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "inscripcion", key = "#id")
    public InscripcionDTO obtenerInscripcionPorId(Long id) {
        return inscripcionRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
    }

    @Override
    @CachePut(value = "inscripcion", key = "#result.idInscripcion")
    @CacheEvict(value = "inscripciones", allEntries = true)
    public InscripcionDTO crearInscripcion(InscripcionDTO dto) {
        if (inscripcionRepository.existsByEstudiante_IdAndMateria_Id(dto.getIdEstudiante(), dto.getIdMateria())) {
            throw new IllegalArgumentException("El estudiante ya está inscrito en esta materia.");
        }

        inscripcionValidator.validarCompletaInscripcion(dto);

        Estudiante estudiante = estudianteRepository.findById(dto.getIdEstudiante())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Materia materia = materiaRepository.findById(dto.getIdMateria())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        Inscripcion inscripcion = convertToEntity(dto, estudiante, materia);
        Inscripcion saved = inscripcionRepository.save(inscripcion);

        return convertToDTO(saved);
    }

    @Override
    @CachePut(value = "inscripcion", key = "#id")
    @CacheEvict(value = "inscripciones", allEntries = true)
    public InscripcionDTO actualizarInscripcion(Long id, InscripcionDTO dto) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        Estudiante nuevoEstudiante = estudianteRepository.findById(dto.getIdEstudiante())
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));

        Materia nuevaMateria = materiaRepository.findById(dto.getIdMateria())
                .orElseThrow(() -> new EntityNotFoundException("Materia no encontrada"));

        inscripcion.setEstudiante(nuevoEstudiante);
        inscripcion.setMateria(nuevaMateria);

        inscripcionValidator.validarEstadoPermitido(dto.getEstado());

        inscripcion.setFechaInscripcion(dto.getFechaInscripcion());
        inscripcion.setEstado(dto.getEstado());
        inscripcion.setUsuarioAlta(dto.getUsuarioAlta());
        inscripcion.setUsuarioBaja(dto.getUsuarioBaja());
        inscripcion.setFechaBaja(dto.getFechaBaja());
        inscripcion.setMotivoBaja(dto.getMotivoBaja());

        Inscripcion actualizada = inscripcionRepository.save(inscripcion);
        return convertToDTO(actualizada);
    }

    @Override
    @CacheEvict(value = { "inscripcion", "inscripciones" }, allEntries = true)
    public InscripcionDTO abandonarInscripcion(Long id, InscripcionDTO dto) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        if ("abandonada".equalsIgnoreCase(inscripcion.getEstado())) {
            throw new IllegalArgumentException("La inscripción ya está en estado 'abandonada'.");
        }

        inscripcion.setEstado("abandonada");
        inscripcion.setUsuarioBaja(dto.getUsuarioBaja() != null ? dto.getUsuarioBaja() : "admin");
        inscripcion.setFechaBaja(LocalDate.now());
        inscripcion.setMotivoBaja(dto.getMotivoBaja());

        Inscripcion abandonada = inscripcionRepository.save(inscripcion);
        return convertToDTO(abandonada);
    }

    // ✅ Convert Entity to DTO
    private InscripcionDTO convertToDTO(Inscripcion inscripcion) {
        return InscripcionDTO.builder()
                .idInscripcion(inscripcion.getIdInscripcion())
                .idEstudiante(inscripcion.getEstudiante().getId())
                .idMateria(inscripcion.getMateria().getId())
                .fechaInscripcion(inscripcion.getFechaInscripcion())
                .estado(inscripcion.getEstado())
                .usuarioAlta(inscripcion.getUsuarioAlta())
                .usuarioBaja(inscripcion.getUsuarioBaja())
                .fechaBaja(inscripcion.getFechaBaja())
                .motivoBaja(inscripcion.getMotivoBaja())
                .build();
    }

    // ✅ Convert DTO to Entity
    private Inscripcion convertToEntity(InscripcionDTO dto, Estudiante estudiante, Materia materia) {
        return Inscripcion.builder()
                .idInscripcion(dto.getIdInscripcion())
                .estudiante(estudiante)
                .materia(materia)
                .fechaInscripcion(dto.getFechaInscripcion() != null ? dto.getFechaInscripcion() : LocalDate.now())
                .estado(dto.getEstado() != null ? dto.getEstado() : "activa")
                .usuarioAlta(dto.getUsuarioAlta() != null ? dto.getUsuarioAlta() : "admin")
                .usuarioBaja(dto.getUsuarioBaja())
                .fechaBaja(dto.getFechaBaja())
                .motivoBaja(dto.getMotivoBaja())
                .build();
    }
}