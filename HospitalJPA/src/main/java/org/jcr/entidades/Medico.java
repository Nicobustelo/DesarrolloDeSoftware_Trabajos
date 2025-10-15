package org.jcr.entidades;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import javax.xml.datatype.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Medico extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Setter(AccessLevel.NONE)
    private Matricula matricula;

    @NonNull
    @NotBlank
    @Setter(AccessLevel.NONE)
    private EspecialidadMedica especialidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @OneToMany(mappedBy="medico", cascade = CascadeType.ALL)
    private final List<Cita> citas = new ArrayList<>();

    @Transient
    private String nroMatricula;
    //@Builder
    //public Medico(String nombre, String apellido, String dni, LocalDate fechaNacimiento,
    //              TipoSangre tipoSangre, String nroMatricula ,EspecialidadMedica especialidad) {
    //    super(nombre, apellido, dni, fechaNacimiento, tipoSangre);
    //    this.matricula = new Matricula(Objects.requireNonNull(nroMatricula, "La matrícula no puede ser nula"));
    //    this.especialidad = Objects.requireNonNull(especialidad, "La especialidad no puede ser nula");
    //}

    protected Medico(MedicoBuilder<?, ?> builder) {
        super(builder);
        // Obtener nroMatricula del builder, no del campo transient
        String matriculaNum = builder.nroMatricula;
        this.matricula = new Matricula(Objects.requireNonNull(matriculaNum, "La matrícula no puede ser nula"));
        
        // Obtener especialidad del builder
        EspecialidadMedica esp = builder.especialidad;
        this.especialidad = Objects.requireNonNull(esp, "La especialidad no puede ser nula");
    }


        public void setDepartamento(Departamento departamento) {
        if (this.departamento != departamento) {
            this.departamento = departamento;
        }
    }

    public void addCita(@NonNull Cita cita) {
        if (!this.citas.contains(cita)) {
            this.citas.add(cita);
        }
        Sala s = cita.getSala();
        if (s != null && !s.getCitas().contains(cita)) {
            s.addCita(cita);
        }
    }

    public void removeCita(@NonNull Cita cita) {
        if (this.citas.contains(cita)) {
            this.citas.remove(cita);
            cita.getSala();
            if (cita.getSala() != null && cita.getSala().getCitas().contains(cita)) {
                cita.getSala().removeCita(cita);
            }
        }else {
            throw new IllegalArgumentException("La cita no pertenece al médico, no puede ser eliminada");
        }
    }


    public List<Cita> getCitas() {
        return Collections.unmodifiableList(new ArrayList<>(citas));
    }



    @Override
    public String toString() {
        return "Medico{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", especialidad=" + especialidad.getDescripcion() +
                ", matricula=" + matricula.getNumero() +
                '}';
    }

    public boolean validarDisponibilidadCita(LocalDateTime fechaHora){
        for (Cita cita : citas) {

            if (Duration.between(cita.getFechaHora(),fechaHora).toHours() < 2) {
                return false;
            }
        }
        return true;
    }



    public void ConsultarHistorialPaciente(){

        for (Cita cita : citas) {
            if(cita.getEstado() == EstadoCita.EN_CURSO){

                Paciente paciente = cita.getPaciente();
                HistoriaClinica historiaClinicaPaciente = paciente.getHistoriaClinica();

                System.out.println("Historia Clinica del paciente " + paciente.getNombre() + " " + paciente.getApellido());
                System.out.println("Fecha de creacion " + historiaClinicaPaciente.getFechaCreacion());
                System.out.println("El paciente tiene los siguientes diagnosticos: " + historiaClinicaPaciente.getDiagnosticos());
                System.out.println("El paciente tiene los siguientes tratamientos: " + historiaClinicaPaciente.getTratamientos());
                System.out.println("\n=================== ALERTA ===================\n");
                System.out.println(" == ALERTA == El paciente tiene las siguientes alergias: " + historiaClinicaPaciente.getAlergias());
                System.out.println("\n==============================================\n");

            }
        }

    }

    public void consultarEspaciosDisponibles(){

        for (ListIterator<Cita> it = citas.listIterator(); it.hasNext(); ) {
            Cita actual = it.next();
            Cita siguiente = it.hasNext() ? citas.get(it.nextIndex()) : null;

            Duration duracion = Duration.between(actual.getFechaHora(), siguiente.getFechaHora().minusHours(2));
            if (duracion.toHours() >= 2) {
                System.out.println("Entre las " + actual.getFechaHora() + " y las " + siguiente.getFechaHora() + " hay horario para un turno");
            }else if (duracion.equals(Duration.ZERO)){
                throw new IllegalArgumentException("El horario ");
            }
        }

    }


}
