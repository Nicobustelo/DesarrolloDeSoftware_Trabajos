package org.jcr; // ajusta al package real

import jakarta.persistence.TypedQuery;
import org.jcr.entidades.*;
import org.jcr.entidades.EspecialidadMedica;
import org.jcr.entidades.Hospital;
import org.jcr.entidades.Matricula;
import org.jcr.entidades.Medico;
import org.jcr.entidades.Paciente;
import org.jcr.entidades.Sala;
import org.jcr.entidades.TipoSangre;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.jcr.entidades.*; 
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hospitalPU");
        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();

            // ================= HOSPITAL 1 =================
            Hospital h1 = Hospital.builder()
                    .nombre("Hospital 631231")
                    .direccion("Av. Siempre Viva 123, Mendoza")
                    .telefono("2614000000")
                    .build();

            var dep1 = Departamento.builder()
                    .nombre("Cardiología")
                    .especialidad(EspecialidadMedica.CARDIOLOGIA)
                    .build();
            h1.agregarDepartamento(dep1); // setea ambos lados
            dep1.crearSala("101","Consultorio");


           

            var p1 = Paciente.builder()
                    .nombre("Lucía")
                    .apellido("Martínez")
                    .dni("41222333")
                    .fechaNacimiento(LocalDate.of(1998, 3, 9))
                    .tipoSangre(TipoSangre.A_POSITIVO)
                    .direccion("San Martín 100")
                    .telefono("2611111111")
                    .build();
            h1.agregarPaciente(p1);

            // ================= HOSPITAL 2 =================
            Hospital h2 = Hospital.builder()
                    .nombre("Hospital del Este")
                    .direccion("Av. Mitre 456, Godoy Cruz")
                    .telefono("2614222222")
                    .build();

            var dep2 = Departamento.builder()
                    .nombre("Pediatría")
                    .especialidad(EspecialidadMedica.PEDIATRIA)
                    .build();
                    h2.agregarDepartamento(dep2);
                    dep2.crearSala("201","Consultorio");



            var p2 = Paciente.builder()
                    .nombre("Carlos")
                    .apellido("Rivas")
                    .dni("43333444")
                    .fechaNacimiento(LocalDate.of(1995, 7, 1))
                    .tipoSangre(TipoSangre.O_NEGATIVO)
                    .direccion("Sarmiento 200")
                    .telefono("2612222222")
                    .build();
            h2.agregarPaciente(p2);
            var p3 = Paciente.builder()
                    .nombre("Manuela")
                    .apellido("Fernandez")
                    .dni("45987638")
                    .fechaNacimiento(LocalDate.of(1985, 9, 2))
                    .tipoSangre(TipoSangre.AB_NEGATIVO)
                    .direccion("chaco 500")
                    .telefono("7896541327")
                    .build();
            h2.agregarPaciente(p3);

            var m1 = Medico.builder()
                    .nombre("Valentina")
                    .apellido("Alvarez")
                    .dni("30111222")
                    .fechaNacimiento(LocalDate.of(1985, 5, 14))
                    .tipoSangre(TipoSangre.O_POSITIVO)
                    .especialidad(EspecialidadMedica.CARDIOLOGIA)
                    .nroMatricula("MP-1239452")
                    .build();
            dep1.agregarMedico(m1);

            var m2 = Medico.builder()
                    .nombre("Juan")
                    .apellido("Gómez")
                    .dni("28999111")
                    .fechaNacimiento(LocalDate.of(1982, 11, 2))
                    .tipoSangre(TipoSangre.B_POSITIVO)
                    .especialidad(EspecialidadMedica.PEDIATRIA)
                    .nroMatricula("MP-1234543")
                    .build();
            dep2.agregarMedico(m2);

            var m3 = Medico.builder()
                    .nombre("Barney")
                    .apellido("Gómez")
                    .dni("28999111")
                    .fechaNacimiento(LocalDate.of(1982, 11, 2))
                    .tipoSangre(TipoSangre.B_POSITIVO)
                    .especialidad(EspecialidadMedica.PEDIATRIA)
                    .nroMatricula("MP-1232943")
                    .build();
            dep2.agregarMedico(m3);



            // ================= PERSISTENCIA =================
            // Persisto solo hospitales (departamentos, salas, médicos y pacientes caen por cascade del aggregate root)
            System.out.println("NOMBRE DEL HOSPITAL: " + h1.getNombre());


            em.persist(h1);
            em.persist(h2);

            // ================= PROGRAMACIÓN DE CITAS =================
            System.out.println("\n===== PROGRAMANDO CITAS MÉDICAS =====");

            // Cita 1: Cardiología - Mariana Pérez con Lucía Martínez
            Cita cita1 = Cita.builder()
                    .paciente(p1)
                    .medico(m1)
                    .sala(dep1.getSalas().get(0))
                    .fechaHora(LocalDateTime.of(2025, 10, 20, 10, 0)) // Fecha futura
                    .costo(new BigDecimal("150000.00"))
                    .build();
            cita1.setEstado(EstadoCita.PROGRAMADA);
            cita1.setObservaciones("Control rutinario de hipertensión arterial");
            em.persist(cita1);
            System.out.println("✓ Cita 1 programada: " + cita1.toString());

            // Cita 2: Pediatría - Juan Gómez con Carlos Rivas
            Cita cita2 = Cita.builder()
                    .paciente(p2)
                    .medico(m2)
                    .sala(dep2.getSalas().get(0))
                    .fechaHora(LocalDateTime.of(2025, 10, 21, 14, 30)) // Fecha futura
                    .costo(new BigDecimal("80000.00"))
                    .build();
            cita2.setEstado(EstadoCita.PROGRAMADA);
            cita2.setObservaciones("Consulta pediátrica de rutina - control de crecimiento");
            em.persist(cita2);
            System.out.println("✓ Cita 2 programada: " + cita2.toString());

            // Cita 3: Pediatría - Barney Gómez con Manuela Fernández
            Cita cita3 = Cita.builder()
                    .paciente(p3)
                    .medico(m3)
                    .sala(dep2.getSalas().get(0))
                    .fechaHora(LocalDateTime.of(2025, 10, 22, 9, 15)) // Fecha futura
                    .costo(new BigDecimal("120000.00"))
                    .build();
                    cita3.setEstado(EstadoCita.PROGRAMADA);
                    cita3.setObservaciones("Consulta por fiebre recurrente - seguimiento");
                    em.persist(cita3);
                    System.out.println("✓ Cita 3 programada: " + cita3.toString());

            // Cita 4: Cardiología - Mariana Pérez con Manuela Fernández (diferente especialidad)
            Cita cita4 = Cita.builder()
                    .paciente(p3)
                    .medico(m1)
                    .sala(dep1.getSalas().get(0))
                    .fechaHora(LocalDateTime.of(2025, 10, 25, 11, 30)) // Fecha futura
                    .costo(new BigDecimal("180000.00"))
                    .build();
            cita4.setEstado(EstadoCita.PROGRAMADA);
            cita4.setObservaciones("Consulta cardiológica por dolor en el pecho");
            em.persist(cita4);
            System.out.println("✓ Cita 4 programada: " + cita4.toString());

            // Cita 5: Cardiología - Mariana Pérez con Manuela Fernández (diferente especialidad)
            Cita cita5 = Cita.builder()
                    .paciente(p2)
                    .medico(m1)
                    .sala(dep1.getSalas().get(0))
                    .fechaHora(LocalDateTime.of(2025, 10, 25, 12, 30)) // Fecha futura
                    .costo(new BigDecimal("200000.00"))
                    .build();
            cita5.setEstado(EstadoCita.PROGRAMADA);
            cita5.setObservaciones("Consulta cardiológica por dolor en el pecho");
            em.persist(cita5);
            System.out.println("✓ Cita 4 programada: " + cita5.toString());

            System.out.println("\n Todas las citas programadas exitosamente\n");


            // Cita 5: Cardiología - Mariana Pérez con Manuela Fernández (diferente especialidad)
            Cita cita6 = Cita.builder()
                    .paciente(p2)
                    .medico(m2)
                    .sala(dep1.getSalas().get(0))
                    .fechaHora(LocalDateTime.of(2025, 10, 25, 12, 30)) // Fecha futura
                    .costo(new BigDecimal("200000.00"))
                    .build();
            cita6.setEstado(EstadoCita.PROGRAMADA);
            cita6.setObservaciones("Consulta cardiológica por dolor en el pecho");
            em.persist(cita6);
            System.out.println("✓ Cita 4 programada: " + cita6.toString());

            System.out.println("\n Todas las citas programadas exitosamente\n");

            em.getTransaction().commit();
            
            // ================= CONSULTAS DESPUÉS DEL COMMIT =================
            System.out.println("\n===== CONSULTA DE HOSPITALES =====");
            String nombre = "Hospital 631231";
            TypedQuery<Hospital> q = em.createQuery(
                    "SELECT h FROM Hospital h WHERE h.nombre = :nombre", Hospital.class);
            q.setParameter("nombre", nombre);
            List<Hospital> hs = q.getResultList();
            System.out.println("Hospitales encontrados: " + hs.size());
            
            // Mostrar médicos de cada hospital
            for (Hospital h : hs) {
                System.out.println("\nHospital: " + h.getNombre());
                for (Departamento dep : h.getDepartamentos()) {
                    System.out.println("  Departamento: " + dep.getNombre());
                    for (Medico med : dep.getMedicos()) {
                        System.out.println("    Médico: " + med.getNombre() + " " + med.getApellido() + 
                                         " - Matrícula: " + med.getMatricula().getNumero());
                    }
                }
            }
            
            // Consulta directa de todos los médicos
            System.out.println("\n===== CONSULTA DIRECTA DE MÉDICOS =====");
            TypedQuery<Medico> qMedicos = em.createQuery("SELECT m FROM Medico m", Medico.class);
            List<Medico> medicos = qMedicos.getResultList();
            System.out.println("Total médicos en BD: " + medicos.size());
            for (Medico m : medicos) {
                System.out.println("  " + m.getNombre() + " " + m.getApellido() + 
                                 " - Matrícula: " + m.getMatricula().getNumero());
            }

            // Consulta de todas las citas
            System.out.println("\n===== CONSULTA DIRECTA DE CITAS =====");
            TypedQuery<Cita> qCitas = em.createQuery("SELECT c FROM Cita c ORDER BY c.fechaHora", Cita.class);
            List<Cita> citas = qCitas.getResultList();
            System.out.println("Total citas en BD: " + citas.size());
            for (Cita c : citas) {
                System.out.println("  " + c.toString());
                if (!c.getObservaciones().isEmpty()) {
                    System.out.println("    Observaciones: " + c.getObservaciones());
                }
            }




        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.out.println("ERROR EN EL COMMIT");
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
            System.out.println("\n🔒 Conexión cerrada. Programa finalizado. ");
        }
    }
}
