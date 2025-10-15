package org.jcr.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Paciente extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private HistoriaClinica historiaClinica;

    @Length(min = 1, max = 20)
    @Setter(AccessLevel.NONE)
    private String telefono;

    @Length(min = 1, max = 300)
    @Setter(AccessLevel.NONE)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy="paciente", cascade = CascadeType.ALL)
    private List<Cita> citas = new ArrayList<>();

    //@Builder
    //public Paciente(String nombre, String apellido, String dni, LocalDate fechaNacimiento, TipoSangre tipoSangre, String telefono, String direccion) {
    //    super(nombre, apellido, dni, fechaNacimiento, tipoSangre);
    //    this.telefono = validarString(telefono, "El teléfono no puede ser nulo ni vacío");
    //    this.direccion = validarString(direccion, "La dirección no puede ser nula ni vacía");
    //    this.historiaClinica = new HistoriaClinica(this);
    //}

    protected Paciente(PacienteBuilder <?, ?> builder){
        super(builder);
        this.historiaClinica = new HistoriaClinica(this);
    }
    public void setHospital(Hospital hospital) {
        if (this.hospital != hospital) {
            if (this.hospital != null) {
                this.hospital.getInternalPacientes().remove(this);
            }
            this.hospital = hospital;
            if (hospital != null) {
                hospital.getInternalPacientes().add(this);
            }
        }
    }

    public List<Cita> getCitasProgramadas() {
        if (citas == null) return Collections.emptyList();
        return citas.stream()
                .filter(c -> c.getEstado() == EstadoCita.PROGRAMADA)
                .toList();
    }
    public List<Cita> getCitasPasadas() {
        if (citas == null) return Collections.emptyList();
        return citas.stream()
                .filter(c -> c.getEstado() == EstadoCita.COMPLETADA
                        || c.getEstado() == EstadoCita.CANCELADA
                        || c.getEstado() == EstadoCita.NO_ASISTIO)
                .collect(Collectors.toUnmodifiableList());
    }
    private String describirCita(Cita cita) {
        return String.format("- %s | Médico: %s %s | Sala: %s | Estado: %s",
                cita.getFechaHora(),
                cita.getMedico().getNombre(),
                cita.getMedico().getApellido(),
                cita.getSala().getNumero(),
                cita.getEstado());
    }
    public void mostrarCitasPorEstado() {
        System.out.println("Citas programadas");
        getCitasProgramadas().forEach(c -> System.out.println(describirCita(c)));

        System.out.println("Cita Pasadas");
        getCitasPasadas().forEach(c -> System.out.println(describirCita(c)));
    }
    public void addCita(Cita cita) {
        this.citas.add(cita);
    }

    public List<Cita> getCitas() {
        return Collections.unmodifiableList(new ArrayList<>(citas));
    }


    private String validarString(String valor, String mensajeError) {
        Objects.requireNonNull(valor, mensajeError);
        if (valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return valor;
    }



    @Override
    public String toString() {
        return "Paciente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tipoSangre=" + tipoSangre.getDescripcion() +
                '}';
    }
}
