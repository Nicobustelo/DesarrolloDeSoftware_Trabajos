package org.jcr.entidades;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "departamento")
public class Sala{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Length(min = 1, max = 20)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(unique = true)
    private String numero;

    @Setter(AccessLevel.NONE)
    @Length(min = 1, max = 100)
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    @Setter(AccessLevel.NONE)
    private Departamento departamento;

    @OneToMany(mappedBy="sala")
    @Column(length = 20, nullable = false, unique = true)

    private final List<Cita> citas = new ArrayList<>();
    @Builder()
    public Sala(String numero, String tipo, @NonNull Departamento departamento) {
        this.numero = validarString(numero, "El número de sala no puede ser nulo ni vacío");
        this.tipo = validarString(tipo, "El tipo de sala no puede ser nulo ni vacío");
        this.departamento = departamento;
    }



    public void addCita(@NonNull Cita cita) {
        // Evita duplicados en la sala
        if (!this.citas.contains(cita)) {
            this.citas.add(cita);
        }


        Medico m = cita.getMedico();
        if (m != null && !m.getCitas().contains(cita)) {
            m.addCita(cita); // OJO: que m.addCita tenga el mismo guard
        }
    }
    public void removeCita(@NonNull Cita cita) {
        if (this.citas.contains(cita)) {
            this.citas.remove(cita);
            if (cita.getMedico() != null && cita.getMedico().getCitas().contains(cita)) {
                cita.getMedico().removeCita(cita);
            }
        }else {
            throw new IllegalArgumentException("La cita no puede ser eliminada");
        }
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

    public boolean esDisponible(LocalDateTime fechaHora){
        for (Cita cita : citas) {
            if (Duration.between(cita.getFechaHora(),fechaHora).toHours() < 2) {
                return false;
            }
        }
        return true;
    }


}
