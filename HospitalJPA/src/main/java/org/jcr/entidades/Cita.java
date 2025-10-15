package org.jcr.entidades;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cita implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.EAGER)
    private Paciente paciente;


    @ManyToOne(fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private Medico medico;

    @ManyToOne(fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private Sala sala;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    @Future
    private LocalDateTime fechaHora;
    
    @Positive
    private BigDecimal costo;

    @Column(nullable = false)
    private EstadoCita estado;

    @Length(min = 0, max = 1000)
    private String observaciones;

    @Builder
    public Cita(Paciente paciente, Medico medico, Sala sala, LocalDateTime fechaHora, BigDecimal costo) {
        this.paciente = Objects.requireNonNull(paciente, "El paciente no puede ser nulo");
        this.medico = Objects.requireNonNull(medico, "El médico no puede ser nulo");
        this.sala = Objects.requireNonNull(sala, "La sala no puede ser nula");
        this.fechaHora = Objects.requireNonNull(fechaHora, "La fecha y hora no pueden ser nulas");
        this.costo = Objects.requireNonNull(costo, "El costo no puede ser nulo");
        this.estado = EstadoCita.PROGRAMADA;

        if(!medico.validarDisponibilidadCita(fechaHora)){

            throw new RuntimeException("Error de disponibilidad: El médico no están disponibles.");

        }else if(!sala.esDisponible(fechaHora)){

            throw new RuntimeException("Error de disponibilidad: La sala no están disponibles.");

        }

        else{
            medico.addCita(this);
        }
    }


    public void setEstado(EstadoCita estado) {
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser nulo");
    }


    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones != null ? observaciones : "";
    }

    @Override
    public String toString() {
        return "Cita{" +
                "paciente=" + paciente.getNombreCompleto() +
                ", medico=" + medico.getNombreCompleto() +
                ", sala=" + sala.getNumero() +
                ", fechaHora=" + fechaHora +
                ", estado=" + estado.getDescripcion() +
                ", costo=" + costo +
                '}';
    }

    public String toCsvString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                paciente.getDni(),
                medico.getDni(),
                sala.getNumero(),
                fechaHora.toString(),
                costo.toString(),
                estado.name(),
                observaciones.replaceAll(",", ";"));
    }

    public static Cita fromCsvString(String csvString,
                                     Map<String, Paciente> pacientes,
                                     Map<String, Medico> medicos,
                                     Map<String, Sala> salas) throws CitaException {
        String[] values = csvString.split(",");
        if (values.length != 7) {
            throw new CitaException("Formato de CSV inválido para Cita: " + csvString);
        }

        String dniPaciente = values[0];
        String dniMedico = values[1];
        String numeroSala = values[2];
        LocalDateTime fechaHora = LocalDateTime.parse(values[3]);
        BigDecimal costo = new BigDecimal(values[4]);
        EstadoCita estado = EstadoCita.valueOf(values[5]);
        String observaciones = values[6].replaceAll(";", ",");

        Paciente paciente = pacientes.get(dniPaciente);
        Medico medico = medicos.get(dniMedico);
        Sala sala = salas.get(numeroSala);

        if (paciente == null) {
            throw new CitaException("Paciente no encontrado: " + dniPaciente);
        }
        if (medico == null) {
            throw new CitaException("Médico no encontrado: " + dniMedico);
        }
        if (sala == null) {
            throw new CitaException("Sala no encontrada: " + numeroSala);
        }

        Cita cita = new Cita(paciente, medico, sala, fechaHora, costo);
        cita.setEstado(estado);
        cita.setObservaciones(observaciones);

        return cita;
    }
    public void iniciarCita(){
        setEstado(EstadoCita.EN_CURSO);
    }


    public void finalizarCita(String observaciones){
        if(this.getObservaciones() == null || this.getObservaciones().isEmpty()){
            this.observaciones = observaciones;
        }else{
            this.observaciones += ", " + observaciones;
        }
        setEstado(EstadoCita.COMPLETADA);
    }


    public void cancelarCita(String motivoCancelacion){
        if (!(this.estado == EstadoCita.PROGRAMADA)){
            throw new RuntimeException("La cita debe estar en estado PROGRAMADA para poder ser cancelada.");
        }
        this.getMedico().removeCita(this);
        this.setObservaciones(motivoCancelacion);
        setEstado(EstadoCita.CANCELADA);
    }


    public void registrarNoAsistencia(){
        this.setObservaciones("CANCELADA POR INASISTENCIA");
        setEstado(EstadoCita.NO_ASISTIO);
    }

    public void reagendarCita(LocalDateTime nuevaFechaHora){
        if (!(this.estado == EstadoCita.CANCELADA || this.estado == EstadoCita.NO_ASISTIO)){
            throw new RuntimeException("No puede reasignarse el horario de una cita completada o en curso");
        }
        if (this.getMedico().validarDisponibilidadCita(nuevaFechaHora) && this.getSala().esDisponible(nuevaFechaHora )){
            this.fechaHora = nuevaFechaHora;

        }else{
            throw new RuntimeException("No se puede reasignar la cida en ese horario");
        }
    }
}
