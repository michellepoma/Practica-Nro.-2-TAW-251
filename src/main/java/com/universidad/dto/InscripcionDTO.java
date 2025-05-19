package com.universidad.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO para la gestión de inscripciones.
 * Usa grupos de validación para controlar qué campos son obligatorios en diferentes operaciones.
 * 
 * - Grupo Alta: Creación y modificación de inscripciones.
 * - Grupo Abandono: Abandono de inscripciones.
 * 
 * Ejemplo de uso en Swagger:
 * - Creación: POST /api/inscripciones
 * - Abandono: PUT /api/inscripciones/{id}/abandonar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para gestionar las inscripciones de estudiantes a materias")
public class InscripcionDTO implements Serializable {

    /** Grupos de validación */
    public interface Alta {}
    public interface Abandono {}

    @Schema(
        description = "Identificador único de la inscripción",
        example = "IFN-110",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idInscripcion;

    @Schema(
        description = "ID del estudiante que realiza la inscripción",
        example = "2001",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El ID del estudiante es obligatorio", groups = Alta.class)
    private Long idEstudiante;

    @Schema(
        description = "ID de la materia a la que se inscribe",
        example = "301",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El ID de la materia es obligatorio", groups = Alta.class)
    private Long idMateria;

    @Schema(
        description = "Fecha de inscripción",
        example = "2025-05-18",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "La fecha de inscripción es obligatoria", groups = Alta.class)
    @PastOrPresent(message = "La fecha de inscripción debe ser anterior o igual a la fecha actual", groups = Alta.class)
    private LocalDate fechaInscripcion;

    @Schema(
        description = "Estado de la inscripción (activa o abandonada)",
        example = "activa",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El estado es obligatorio", groups = Alta.class)
    @Pattern(
        regexp = "^(activa|abandonada)$", 
        message = "El estado debe ser 'activa' o 'abandonada'", 
        groups = Alta.class
    )
    private String estado;

    @Schema(
        description = "Usuario que dio de alta la inscripción",
        example = "admin",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El usuario de alta es obligatorio", groups = Alta.class)
    @Size(min = 3, max = 50, message = "El usuario de alta debe tener entre 3 y 50 caracteres", groups = Alta.class)
    private String usuarioAlta;

    @Schema(
        description = "Usuario que realiza la baja de la inscripción",
        example = "adminBaja",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El usuario de baja es obligatorio", groups = Abandono.class)
    @Size(min = 3, max = 50, message = "El usuario de baja debe tener entre 3 y 50 caracteres", groups = Abandono.class)
    private String usuarioBaja;

    @Schema(
        description = "Fecha de baja de la inscripción",
        example = "2025-07-01"
    )
    @FutureOrPresent(message = "La fecha de baja debe ser mayor o igual a la fecha actual", groups = Abandono.class)
    private LocalDate fechaBaja;

    @Schema(
        description = "Motivo de baja de la inscripción (renuncia, desercion, traslado)",
        example = "renuncia",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El motivo de baja es obligatorio", groups = Abandono.class)
    @Size(min = 3, max = 100, message = "El motivo de baja debe tener entre 3 y 100 caracteres", groups = Abandono.class)
    private String motivoBaja;
}
