package org.jcr.entidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)


public class Departamento{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Length(min = 1,max = 150)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    @NotNull
    private EspecialidadMedica especialidad;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    @NotNull
    private Hospital hospital;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    private final List<Medico> medicos = new ArrayList<>();

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    private final List<Sala> salas = new ArrayList<>();

    @Builder
    public Departamento(String nombre, EspecialidadMedica especialidad) {
        this.nombre = validarString(nombre, "El nombre del departamento no puede ser nulo ni vacío");
        this.especialidad = Objects.requireNonNull(especialidad, "La especialidad no puede ser nula");
    }

    public void setHospital(Hospital hospital) {
        if (this.hospital != hospital) {
            if (this.hospital != null) {
                this.hospital.getInternalDepartamentos().remove(this);
            }
            this.hospital = hospital;
            if (hospital != null) {
                hospital.getInternalDepartamentos().add(this);
            }
        }
    }

    public void agregarMedico(Medico medico) {
        if (medico != null && !medicos.contains(medico)) {
            if (medico.getEspecialidad().equals(this.especialidad)) {
            medicos.add(medico);
            medico.setDepartamento(this);
            }
            else{
                throw new  IllegalArgumentException("El medico debe tener la misma especialidad que el departamento");
            }
        }
    }

    public Sala crearSala(String numero, String tipo) {
        if (salas.stream().anyMatch(s -> s.getNumero().equals(numero))) {

            throw new IllegalArgumentException("Ya existe una sala con el número " + numero);
        }
        Sala sala = new Sala(numero, tipo, this);
        salas.add(sala);
        return sala;
    }


    public List<Medico> getMedicos() {
        return Collections.unmodifiableList(medicos);
    }

    public List<Sala> getSalas() {
        return Collections.unmodifiableList(salas);
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "nombre='" + nombre + '\'' +
                ", especialidad=" + especialidad.getDescripcion() +
                ", hospital=" + (hospital != null ? hospital.getNombre() : "null") +
                '}';
    }

    private String validarString(String valor, String mensajeError) {
        Objects.requireNonNull(valor, mensajeError);
        if (valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return valor;
    }
}
