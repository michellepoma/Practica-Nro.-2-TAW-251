package com.universidad.controller;

import com.universidad.dto.InscripcionDTO;
import com.universidad.service.IInscripcionService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@Validated
public class InscripcionController {

    private final IInscripcionService inscripcionService;
    private static final Logger logger = LoggerFactory.getLogger(InscripcionController.class);

    @Autowired
    public InscripcionController(IInscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @GetMapping
    public ResponseEntity<List<InscripcionDTO>> obtenerTodasLasInscripciones() {
        long inicio = System.currentTimeMillis();
        logger.info("[INSCRIPCION] Inicio obtenerTodasLasInscripciones: {}", inicio);
        List<InscripcionDTO> inscripciones = inscripcionService.obtenerTodasLasInscripciones();
        long fin = System.currentTimeMillis();
        logger.info("[INSCRIPCION] Fin obtenerTodasLasInscripciones: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(inscripciones);
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<List<InscripcionDTO>> obtenerInscripcionesPorEstudiante(@PathVariable Long idEstudiante) {
        logger.info("[INSCRIPCION] Consultando inscripciones por estudiante: {}", idEstudiante);
        return ResponseEntity.ok(inscripcionService.obtenerInscripcionesPorEstudiante(idEstudiante));
    }

    @GetMapping("/materia/{idMateria}")
    public ResponseEntity<List<InscripcionDTO>> obtenerInscripcionesPorMateria(@PathVariable Long idMateria) {
        logger.info("[INSCRIPCION] Consultando inscripciones por materia: {}", idMateria);
        return ResponseEntity.ok(inscripcionService.obtenerInscripcionesPorMateria(idMateria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> obtenerInscripcionPorId(@PathVariable Long id) {
        logger.info("[INSCRIPCION] Consultando inscripción por ID: {}", id);
        return ResponseEntity.ok(inscripcionService.obtenerInscripcionPorId(id));
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InscripcionDTO> crearInscripcion(
            @Validated(InscripcionDTO.Alta.class) @RequestBody InscripcionDTO inscripcionDTO) {

        logger.info("[INSCRIPCION] Creando inscripción para estudiante ID: {}, materia ID: {}",
                inscripcionDTO.getIdEstudiante(), inscripcionDTO.getIdMateria());
        InscripcionDTO nuevaInscripcion = inscripcionService.crearInscripcion(inscripcionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcion);
    }

    @PutMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InscripcionDTO> actualizarInscripcion(
            @PathVariable Long id,
            @Validated(InscripcionDTO.Alta.class) @RequestBody InscripcionDTO inscripcionDTO) {

        logger.info("[INSCRIPCION] Actualizando inscripción ID: {}", id);
        InscripcionDTO actualizada = inscripcionService.actualizarInscripcion(id, inscripcionDTO);
        return ResponseEntity.ok(actualizada);
    }

    @PutMapping("/{id}/abandonar")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InscripcionDTO> abandonarInscripcion(
            @PathVariable Long id,
            @Validated(InscripcionDTO.Abandono.class) @RequestBody InscripcionDTO inscripcionDTO) {

        logger.info("[INSCRIPCION] Abandonando inscripción ID: {}", id);
        InscripcionDTO abandonada = inscripcionService.abandonarInscripcion(id, inscripcionDTO);
        return ResponseEntity.ok(abandonada);
    }
}
