package com.universidad.service.impl;

import com.universidad.model.Docente;
import com.universidad.model.Materia;
import com.universidad.repository.DocenteRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IMateriaService;
import com.universidad.validation.MateriaValidator;

import jakarta.transaction.Transactional;

import com.universidad.dto.MateriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private MateriaValidator materiaValidator;


    // Método utilitario para mapear Materia a MateriaDTO
    private MateriaDTO mapToDTO(Materia materia) {
        if (materia == null) return null;

        return MateriaDTO.builder()
                .id(materia.getId())
                .nombreMateria(materia.getNombreMateria())
                .codigoUnico(materia.getCodigoUnico())
                .creditos(materia.getCreditos())
                .prerequisitos(
                    materia.getPrerequisitos() != null ? 
                    materia.getPrerequisitos().stream().map(Materia::getId).collect(Collectors.toList()) : null
                )
                .esPrerequisitoDe(
                    materia.getEsPrerequisitoDe() != null ? 
                    materia.getEsPrerequisitoDe().stream().map(Materia::getId).collect(Collectors.toList()) : null
                )
                // ✅ Aquí mapeas los números de empleado de los docentes
                .docentesNroEmpleado(
                    materia.getDocentes() != null ?
                    materia.getDocentes().stream()
                        .map(Docente::getNroEmpleado)
                        .collect(Collectors.toList()) 
                    : null
                )
                .build();
    }


    @Override
    @Cacheable(value = "materias")
    public List<MateriaDTO> obtenerTodasLasMaterias() {
        return materiaRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "materia", key = "#id")
    public MateriaDTO obtenerMateriaPorId(Long id) {
        return materiaRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    @Override
    @Cacheable(value = "materia", key = "#codigoUnico")
    public MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico) {
        Materia materia = materiaRepository.findByCodigoUnico(codigoUnico);
        return mapToDTO(materia);
    }

    @Override
    @CachePut(value = "materia", key = "#result.id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO crearMateria(MateriaDTO materiaDTO) {
        materiaValidator.validacionCompletaMateria(materiaDTO);
        Materia materia = new Materia();
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        // Map other fields as necessary
        Materia savedMateria = materiaRepository.save(materia);
        return mapToDTO(savedMateria);
    }

    @Override
    @Transactional
    @CachePut(value = "materia", key = "#id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO actualizarMateria(Long id, MateriaDTO materiaDTO) {
        Materia materia = materiaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        Materia updatedMateria = materiaRepository.save(materia);
        return mapToDTO(updatedMateria);
    }


    @Override
    @CacheEvict(value = {"materia", "materias"}, allEntries = true)
    public void eliminarMateria(Long id) {
        materiaRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CachePut(value = "materia", key = "#materiaId")
    @CacheEvict(value = {"materia", "materias"}, allEntries = true)
    public MateriaDTO asignarDocentesAMateria(Long materiaId, List<String> docentesNroEmpleado) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        List<Docente> docentes = docenteRepository.findByNroEmpleadoIn(docentesNroEmpleado);

        materia.setDocentes(docentes);
        Materia actualizada = materiaRepository.save(materia);

        return mapToDTO(actualizada);
    }

}
