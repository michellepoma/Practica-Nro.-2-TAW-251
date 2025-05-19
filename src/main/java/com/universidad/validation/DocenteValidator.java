package com.universidad.validation;

import org.springframework.stereotype.Component;

import com.universidad.dto.DocenteDTO;
import com.universidad.repository.DocenteRepository;

import java.util.Arrays;
import java.util.List;

@Component // Indica que esta clase es un componente de Spring
public class DocenteValidator {

    private final DocenteRepository docenteRepository;

    public DocenteValidator(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    // Valida que el email sea único entre docentes
    public void validaEmailUnico(String email) {
        if (docenteRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un docente con este email.");
        }
    }

    // Valida el dominio del email
    public void validaDominioEmail(String email) {
        String dominio = email.substring(email.indexOf('@') + 1);
        List<String> dominiosBloqueados = Arrays.asList("dominiobloqueado.com", "spam.com");

        if (dominiosBloqueados.contains(dominio)) {
            throw new IllegalArgumentException("El dominio de email no está permitido.");
        }
    }

    // Valida que el número de empleado sea único
    public void validaNumeroEmpleadoUnico(String nroEmpleado) {
        if (docenteRepository.existsByNroEmpleado(nroEmpleado)) {
            throw new IllegalArgumentException("El número de empleado ya existe.");
        }
    }

    public void validaNombreDocente(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del docente no puede estar vacío o nulo.");
        }
    }

    public void validaApellidoDocente(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido del docente es obligatorio y no puede estar vacío.");
        }
    }

    public void validaDepartamento(String departamento) {
        if (departamento == null || departamento.trim().isEmpty()) {
            throw new IllegalArgumentException("El departamento es obligatorio.");
        }
    }

    // Validación completa
    public void validacionCompletaDocente(DocenteDTO docente) {
        validaEmailUnico(docente.getEmail());
        validaDominioEmail(docente.getEmail());
        validaNumeroEmpleadoUnico(docente.getNroEmpleado());
        validaNombreDocente(docente.getNombre());
        validaApellidoDocente(docente.getApellido());
        validaDepartamento(docente.getDepartamento());
        // Puedes agregar más validaciones si lo consideras necesario
    }

    // Excepción personalizada (opcional, si prefieres no lanzar IllegalArgumentException)
    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
}
