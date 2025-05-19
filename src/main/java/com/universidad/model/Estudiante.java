package com.universidad.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Entidad JPA que representa a un estudiante en el sistema universitario.
 * Hereda los atributos comunes de la clase Persona.
 * Incluye información de inscripción, estado, fechas y materias asociadas.
 */
@Entity
@Table(name = "estudiante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "id_persona") // Importante para herencia JOINED
public class Estudiante extends Persona {

    @Column(name = "numero_inscripcion", nullable = false, unique = true)
    private String numeroInscripcion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "usuario_alta")
    private String usuarioAlta;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;

    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    @Column(name = "usuario_baja")
    private String usuarioBaja;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @Column(name = "motivo_baja")
    private String motivoBaja;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "estudiante_materia",
        joinColumns = @JoinColumn(name = "id_persona"),
        inverseJoinColumns = @JoinColumn(name = "id_materia")
    )
    private List<Materia> materias;
}
