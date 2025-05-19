package com.universidad.validation;

import com.universidad.dto.MateriaDTO;
import com.universidad.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MateriaValidator {

    @Autowired
    private MateriaRepository materiaRepository;

    public void validarCodigoUnico(String codigoUnico) {
        if (materiaRepository.findByCodigoUnico(codigoUnico) != null) {
            throw new IllegalArgumentException("El código único ya existe: " + codigoUnico);
        }
    }

    public void validarCreditos(Integer creditos) {
        if (creditos == null || creditos < 1 || creditos > 10) {
            throw new IllegalArgumentException("Los créditos deben ser un valor entre 1 y 10.");
        }
    }

    public void validacionCompletaMateria(MateriaDTO materiaDTO) {
        validarCodigoUnico(materiaDTO.getCodigoUnico());
        validarCreditos(materiaDTO.getCreditos());
    }
}
