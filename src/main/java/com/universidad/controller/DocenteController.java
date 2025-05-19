package com.universidad.controller;

import com.universidad.dto.DocenteDTO;
import com.universidad.dto.MateriaDTO;
import com.universidad.model.Materia;
import com.universidad.service.IDocenteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    private static final Logger logger = LoggerFactory.getLogger(DocenteController.class);

    @Autowired
    private IDocenteService docenteService;

    @GetMapping
    public ResponseEntity<List<DocenteDTO>> obtenerTodosLosDocentes() {
        long inicio = System.currentTimeMillis();
        logger.info("[DOCENTE] Inicio obtenerTodosLosDocentes: {}", inicio);
        List<DocenteDTO> result = docenteService.obtenerTodosLosDocentes();
        long fin = System.currentTimeMillis();
        logger.info("[DOCENTE] Fin obtenerTodosLosDocentes: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/empleado/{nroEmpleado}")
    public ResponseEntity<DocenteDTO> obtenerDocentePorNumeroEmpleado(@PathVariable String nroEmpleado) {
        DocenteDTO docente = docenteService.obtenerDocentePorNumeroEmpleado(nroEmpleado);
        if (docente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(docente);
    }

    @PostMapping
    public ResponseEntity<DocenteDTO> crearDocente(@RequestBody DocenteDTO docenteDTO) {
        DocenteDTO nuevo = docenteService.crearDocente(docenteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocenteDTO> actualizarDocente(@PathVariable Long id, @RequestBody DocenteDTO docenteDTO) {
        DocenteDTO actualizado = docenteService.actualizarDocente(id, docenteDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDocente(@PathVariable Long id, @RequestBody DocenteDTO docenteDTO) {
        docenteService.eliminarDocente(id, docenteDTO);
        return ResponseEntity.noContent().build();
    }

   @GetMapping("/{nroEmpleado}/materias")
    public ResponseEntity<List<MateriaDTO>> obtenerMateriasPorDocente(@PathVariable String nroEmpleado) {
        long inicio = System.currentTimeMillis();
        logger.info("[DOCENTE] Inicio obtenerMateriasPorDocente: {}", inicio);

        DocenteDTO docente = docenteService.obtenerDocentePorNumeroEmpleado(nroEmpleado);
        if (docente == null) {
            return ResponseEntity.notFound().build();
        }

        // Asumiendo que tienes este método implementado en docenteService
        List<Materia> materias = docenteService.obtenerMateriasDeDocentePorNroEmpleado(nroEmpleado);

        // Convertir las materias a DTO si es necesario
        List<MateriaDTO> materiasDTO = materias.stream()
                .map(m -> MateriaDTO.builder()
                        .id(m.getId())
                        .nombreMateria(m.getNombreMateria())
                        .codigoUnico(m.getCodigoUnico())
                        .creditos(m.getCreditos())
                        .build())
                .collect(Collectors.toList());

        long fin = System.currentTimeMillis();
        logger.info("[DOCENTE] Fin obtenerMateriasPorDocente: {} (Duracion: {} ms)", fin, (fin - inicio));

        return ResponseEntity.ok(materiasDTO);
    }

}