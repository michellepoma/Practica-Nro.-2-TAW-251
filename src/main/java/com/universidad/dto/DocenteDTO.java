package com.universidad.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para la gestión de datos de docentes en la universidad")
public class DocenteDTO implements Serializable {

    @Schema(
        description = "Identificador único del docente",
        example = "1001",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(description = "Nombre del docente", example = "Laura")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Apellido del docente", example = "García")
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 caracteres")
    private String apellido;

    @Schema(description = "Correo electrónico del docente", example = "laura.garcia@universidad.com")
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es válido")
    @Size(max = 100, message = "El email no puede tener más de 100 caracteres")
    private String email;

    @Schema(description = "Fecha de nacimiento del docente", example = "1985-07-15")
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private LocalDate fechaNacimiento;

    @Schema(description = "Número de empleado asignado al docente", example = "EMP12345")
    @NotBlank(message = "El número de empleado es obligatorio")
    @Size(min = 5, max = 20, message = "El número de empleado debe tener entre 5 y 20 caracteres")
    private String nroEmpleado;

    @Schema(description = "Departamento al que pertenece el docente", example = "Matemáticas")
    @NotBlank(message = "El departamento es obligatorio")
    private String departamento;

    @Schema(
        description = "Estado actual del docente (activo o inactivo)",
        example = "activo"
    )
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(activo|inactivo)$", message = "El estado debe ser 'activo' o 'inactivo'")
    private String estado;

    @Schema(description = "Usuario que dio de alta al docente", example = "admin")
    @NotBlank(message = "El usuario de alta es obligatorio")
    @Size(min = 3, max = 50, message = "El usuario de alta debe tener entre 3 y 50 caracteres")
    private String usuarioAlta;

    @Schema(description = "Fecha en la que se dio de alta al docente", example = "2025-05-18")
    @NotNull(message = "La fecha de alta es obligatoria")
    @PastOrPresent(message = "La fecha de alta debe ser anterior o igual a la fecha actual")
    private LocalDate fechaAlta;

    @Schema(description = "Usuario que realizó la última modificación", example = "editorUser")
    @Size(min = 3, max = 50, message = "El usuario de modificación debe tener entre 3 y 50 caracteres")
    private String usuarioModificacion;

    @Schema(description = "Fecha de la última modificación", example = "2025-06-01")
    @FutureOrPresent(message = "La fecha de modificación debe ser mayor o igual a la fecha actual")
    private LocalDate fechaModificacion;

    @Schema(description = "Usuario que dio de baja al docente", example = "adminBaja")
    @Size(min = 3, max = 50, message = "El usuario de baja debe tener entre 3 y 50 caracteres")
    private String usuarioBaja;

    @Schema(description = "Fecha en la que se dio de baja al docente", example = "2025-07-01")
    @FutureOrPresent(message = "La fecha de baja debe ser mayor o igual a la fecha actual")
    private LocalDate fechaBaja;

    @Schema(
        description = "Motivo de baja del docente (renuncia, desercion, traslado)",
        example = "renuncia"
    )
    @Size(min = 3, max = 100, message = "El motivo de baja debe tener entre 3 y 100 caracteres")
    @Pattern(
        regexp = "^(renuncia|desercion|traslado)$",
        message = "El motivo de baja debe ser 'renuncia', 'desercion' o 'traslado'"
    )
    private String motivoBaja;
}
