package org.jcr.entidades;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.io.Serializable;
import java.util.*;

import lombok.*;
import org.hibernate.validator.constraints.Length;


@Getter
@ToString(exclude = {"departamentos", "pacientes"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // opcional
@Entity
@AllArgsConstructor

public class Hospital{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    @Length(min = 1, max = 200)
    private String nombre;

    @NotBlank
    @Length(min = 1, max = 300)
    @Pattern(regexp = "^\\d+$", message = "Solo dígitos")
    private String direccion;

    @NotBlank
    @Length(min = 7, max = 20)
    @Pattern(regexp = "^\\d+$", message = "El numero ingresado contiene digitos no validos")
    private String telefono;


    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Departamento> departamentos = new ArrayList<>();

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paciente> pacientes = new ArrayList<>();

    @Builder(toBuilder = true)
    public Hospital(String nombre,String direccion,String telefono) {
        this.nombre = validarString(nombre, "El nombre del hospital no puede ser nulo ni vacío");
        this.direccion = validarString(direccion, "La dirección no puede ser nula ni vacía");
        this.telefono = validarString(telefono, "El teléfono no puede ser nulo ni vacío");
    }


    public void agregarDepartamento(@NonNull Departamento departamento) {
        if (departamentos.stream().anyMatch(d -> d.getNombre().equals(departamento.getNombre()))) {
            throw new IllegalArgumentException("Ya existe un departamento con el nombre " + departamento.getNombre());
        }
        departamentos.add(departamento);
        departamento.setHospital(this);
    }

    public Optional<Departamento> getDepartamentoPorTipo(@NonNull String nombre) {
        if (departamentos == null) return Optional.empty();
        for (Departamento d : departamentos) {
            if (d != null && nombre.equals(d.getNombre())) {
                return Optional.of(d);
            }
        }
        return Optional.empty();
    }


    public void agregarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo");
        }

        if (pacientes.stream().anyMatch(p -> p.getDni().equals(paciente.getDni()))) {
            throw new IllegalStateException("Ya existe un paciente con DNI " + paciente.getDni());
        }

        pacientes.add(paciente);
        paciente.setHospital(this);
    }

    public List<Departamento> getDepartamentos() {
        return Collections.unmodifiableList(departamentos);
    }

    public List<Paciente> getPacientes() {
        return Collections.unmodifiableList(pacientes);
    }

    List<Departamento> getInternalDepartamentos() {
        return departamentos;
    }

    List<Paciente> getInternalPacientes() {
        return pacientes;
    }


    private static String validarString(String valor, String mensajeError) {
        Objects.requireNonNull(valor, mensajeError);
        if (valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return valor;
    }
}
