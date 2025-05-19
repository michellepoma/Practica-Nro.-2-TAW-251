package com.universidad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "docente")
public class Docente extends Persona {

    @Column(name = "nro_empleado", nullable = false, unique = true)
    @NotNull(message = "El número de empleado es obligatorio")
    @Size(min = 5, max = 20, message = "El número de empleado debe tener entre 5 y 20 caracteres")
    private String nroEmpleado;

    @Column(name = "departamento", nullable = false)
    @NotNull(message = "El departamento es obligatorio")
    private String departamento;

    @Column(name = "estado", nullable = false)
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


    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluacionDocente> evaluaciones;

    @ManyToMany(mappedBy = "docentes")
    private List<Materia> materias;


}

