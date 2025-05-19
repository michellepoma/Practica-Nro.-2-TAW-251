package com.universidad.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
/**
 * Entidad JPA que representa la inscripcion de un estudiante a varias materias en el sistema universitario.
 * Incluye información de inscripción, estado, fechas y materias asociadas.
 */
@Entity
@Table(name = "estudiante_materia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    private Long idInscripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false)
    @NotNull(message = "El estudiante es obligatorio")
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materia", nullable = false)
    @NotNull(message = "La materia es obligatoria")
    private Materia materia;

    @Column(name = "fecha_inscripcion", nullable = false)
    @NotNull(message = "La fecha de inscripción es obligatoria")
    @PastOrPresent(message = "La fecha de inscripción debe ser anterior o igual a la fecha actual")
    @Builder.Default
    private LocalDate fechaInscripcion = LocalDate.now();

    @Column(name = "estado", nullable = false)
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(activa|abandonada)$", message = "El estado debe ser 'activa' o 'abandonada'")
    @Builder.Default
    private String estado = "activa";

    @Column(name = "usuario_alta", nullable = false)
    @NotBlank(message = "El usuario de alta es obligatorio")
    private String usuarioAlta;

    @Column(name = "usuario_baja")
    @Size(min = 3, max = 50, message = "El usuario de baja debe tener entre 3 y 50 caracteres")
    private String usuarioBaja;

    @Column(name = "fecha_baja")
    @FutureOrPresent(message = "La fecha de baja debe ser mayor o igual a la fecha actual")
    private LocalDate fechaBaja;

    @Column(name = "motivo_baja")
    @Size(min = 3, max = 100, message = "El motivo de baja debe tener entre 3 y 100 caracteres")
    private String motivoBaja;
}
