package com.universidad.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la gestión de materias en la universidad.
 * Incluye información sobre créditos, prerequisitos y docentes asignados.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para la gestión de materias universitarias")
public class MateriaDTO implements Serializable {

    @Schema(
        description = "Identificador único de la materia",
        example = "501",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
        description = "Nombre de la materia",
        example = "Álgebra Lineal"
    )
    @NotBlank(message = "El nombre de la materia es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombreMateria;

    @Schema(
        description = "Código único que identifica la materia",
        example = "MAT101"
    )
    @NotBlank(message = "El código único es obligatorio")
    @Size(max = 20, message = "El código único no puede tener más de 20 caracteres")
    private String codigoUnico;

    @Schema(
        description = "Cantidad de créditos académicos que otorga la materia",
        example = "4"
    )
    private Integer creditos;

    @Schema(
        description = "Lista de IDs de materias que son prerequisitos para esta materia",
        example = "[100, 101]"
    )
    private List<Long> prerequisitos;

    @Schema(
        description = "Lista de IDs de materias para las que esta materia es prerequisito",
        example = "[502, 503]"
    )
    private List<Long> esPrerequisitoDe;

    @Schema(
        description = "Números de empleado de los docentes asignados a esta materia",
        example = "[\"EMP12345\", \"EMP67890\"]"
    )
    private List<String> docentesNroEmpleado;
}
