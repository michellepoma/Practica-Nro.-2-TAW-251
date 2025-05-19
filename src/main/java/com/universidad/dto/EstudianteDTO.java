package com.universidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * DTO que representa los datos de un estudiante para transferencia entre capas.
 * Incluye validaciones para los campos principales y datos de inscripción, estado y fechas.
 * 
 * @author Universidad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para la gestión de estudiantes de la universidad")
public class EstudianteDTO implements Serializable {

    @Schema(
        description = "Identificador único del estudiante",
        example = "EST92001",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(description = "Nombre del estudiante", example = "Carlos")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Apellido del estudiante", example = "Ramírez")
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 caracteres")
    private String apellido;

    @Schema(description = "Correo electrónico del estudiante", example = "carlos.ramirez@universidad.com")
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    @Size(max = 100, message = "El email no puede tener más de 100 caracteres")
    private String email;

    @Schema(description = "Fecha de nacimiento del estudiante", example = "1995-04-20")
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private LocalDate fechaNacimiento;

    @Schema(description = "Número de inscripción único del estudiante", example = "EST2025001")
    @NotBlank(message = "El número de inscripción es obligatorio")
    @Size(min = 5, max = 20, message = "El número de inscripción debe tener entre 5 y 20 caracteres")
    private String numeroInscripcion;

    @Schema(
        description = "Estado actual del estudiante (activo o inactivo)", 
        example = "activo"
    )
    @NotBlank(message = "El estado es obligatorio")
    @Size(min = 3, max = 20, message = "El estado debe tener entre 3 y 20 caracteres")
    @Pattern(regexp = "^(activo|inactivo)$", message = "El estado debe ser 'activo' o 'inactivo'")
    private String estado;

    @Schema(description = "Usuario que dio de alta al estudiante", example = "admin")
    @NotBlank(message = "El usuario de alta es obligatorio")
    @Size(min = 3, max = 50, message = "El usuario de alta debe tener entre 3 y 50 caracteres")
    private String usuarioAlta;

    @Schema(description = "Fecha en la que se dio de alta al estudiante", example = "2025-05-18")
    @NotNull(message = "La fecha de alta es obligatoria")
    @PastOrPresent(message = "La fecha de alta debe ser anterior o igual a la fecha actual")
    private LocalDate fechaAlta;

    @Schema(description = "Usuario que realizó la última modificación", example = "editorUser")
    @Size(min = 3, max = 50, message = "El usuario de modificacion debe tener entre 3 y 50 caracteres")
    private String usuarioModificacion;

    @Schema(description = "Fecha de la última modificación", example = "2025-06-01")
    @FutureOrPresent(message = "La fecha de modificacion debe ser mayor o igual a la fecha actual")
    private LocalDate fechaModificacion;

    @Schema(description = "Usuario que dio de baja al estudiante", example = "adminBaja")
    @Size(min = 3, max = 50, message = "El usuario de baja debe tener entre 3 y 50 caracteres")
    private String usuarioBaja;

    @Schema(description = "Fecha en la que se dio de baja al estudiante", example = "2025-07-01")
    @FutureOrPresent(message = "La fecha de baja debe ser mayor o igual a la fecha actual")
    private LocalDate fechaBaja;

    @Schema(
        description = "Motivo de baja del estudiante (renuncia, desercion, traslado)",
        example = "renuncia"
    )
    @Size(min = 3, max = 100, message = "El motivo de baja debe tener entre 3 y 100 caracteres")
    @Pattern(
        regexp = "^(renuncia|desercion|traslado)$", 
        message = "El motivo de baja debe ser 'renuncia', 'desercion' o 'traslado'"
    )
    private String motivoBaja;
}
