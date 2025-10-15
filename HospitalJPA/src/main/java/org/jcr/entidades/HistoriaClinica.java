package org.jcr.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoriaClinica{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    @Setter(AccessLevel.NONE)
    private String numeroHistoria;

    @OneToOne
    @Setter(AccessLevel.NONE)
    @PrimaryKeyJoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Column()
    private LocalDateTime fechaCreacion;

    @CollectionTable
    @ElementCollection
    @Setter(AccessLevel.NONE)
    @OrderColumn(name = "orden_diagnostico")
    private List<String> diagnosticos = new ArrayList<>();

    @CollectionTable
    @ElementCollection
    @Setter(AccessLevel.NONE)
    @OrderColumn(name = "orden_tratamientos")
    private List<String> tratamientos = new ArrayList<>();

    @CollectionTable
    @ElementCollection
    @Setter(AccessLevel.NONE)
    @OrderColumn(name = "orden_alergias")
    private List<String> alergias = new ArrayList<>();

    @Builder
    public HistoriaClinica(Paciente paciente) {
        this.paciente = paciente;
        this.numeroHistoria = generarNumeroHistoria(paciente);
        this.fechaCreacion = LocalDateTime.now();

    }

    private String generarNumeroHistoria(Paciente paciente) {
        return "HC-" + paciente.getDni() + "-" + LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }



    public void agregarDiagnostico(String diagnostico) {
       if(diagnostico != null && !diagnostico.trim().isEmpty()){
           if (diagnostico.trim().length() <= 500){
               diagnosticos.add(diagnostico);
           }else{
               throw new IllegalArgumentException("El diagnóstico no puede superar los 500 caracteres");
           }
       }else{
           throw new IllegalArgumentException("El diagnóstico no puede ser nulo ni vacío");
       }
    }

    public void agregarTratamiento(String tratamiento) {
        if (tratamiento != null && !tratamiento.trim().isEmpty()) {
            if(tratamiento.trim().length() <= 500){
               tratamientos.add(tratamiento);
            }else{
                throw new IllegalArgumentException("El tratamiento no puede superar los 500 caracteres");
            }
        }else{
            throw new IllegalArgumentException("El tratamiento no puede ser nulo ni vacío");
        }
    }

    public void agregarAlergia(String alergia) {
        if (alergia != null && !alergia.trim().isEmpty()) {
            if (alergia.trim().length() <= 200) {
                alergias.add(alergia);
            } else {
                throw new IllegalArgumentException("La alergia no puede superar los 200 caracteres");
            }
        } else {
            throw new IllegalArgumentException("La alergia no puede ser nula ni vacía");
        }
    }
    public List<String> getDiagnosticos() {
        return Collections.unmodifiableList(diagnosticos);
    }

    public List<String> getTratamientos() {
        return Collections.unmodifiableList(tratamientos);
    }

    public List<String> getAlergias() {
        return Collections.unmodifiableList(alergias);
    }

    @Override
    public String toString() {
        return "HistoriaClinica{" +
                "numeroHistoria='" + numeroHistoria + '\'' +
                ", paciente=" + paciente.getNombreCompleto() +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
