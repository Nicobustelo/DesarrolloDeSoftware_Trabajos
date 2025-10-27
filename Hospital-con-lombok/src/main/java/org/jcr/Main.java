
package org.jcr;

import org.jcr.entidades.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("===== SISTEMA DE GESTIÓN HOSPITALARIA =====\n");

        try {
            // 1. Inicializar el hospital y su estructura
            Hospital hospital = inicializarHospital();

            // 2. Crear y configurar médicos
            List<Medico> medicos = crearMedicos(hospital);

            // 3. Registrar pacientes
            List<Paciente> pacientes = registrarPacientes(hospital);

            // 4. Programar citas médicas
            CitaManager citaManager = new CitaManager();
            programarCitas(citaManager, medicos, pacientes, hospital);

            // 5. Mostrar información del sistema
            mostrarInformacionCompleta(hospital, citaManager);

            // 6. Probar persistencia de datos
            probarPersistencia(citaManager, pacientes, medicos, hospital);

            // 7. Ejecutar pruebas de validación
            ejecutarPruebasValidacion(citaManager, medicos, pacientes, hospital);

            // 8. Mostrar estadísticas finales
            mostrarEstadisticasFinales(hospital);

            System.out.println("\n===== SISTEMA EJECUTADO EXITOSAMENTE =====");

        } catch (Exception e) {
            System.err.println("Error en el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===== MÉTODOS DE INICIALIZACIÓN =====

    private static Hospital inicializarHospital() {
        System.out.println("Inicializando hospital y departamentos...");

        // Crear hospital principal
    Hospital hospital = new Hospital("Hospital Regional Norte", "Av. San Martín 987", "011-7890-1234");

        // Crear departamentos especializados
    Departamento cardiologia = new Departamento("Cardiología Integral", EspecialidadMedica.CARDIOLOGIA);
    Departamento pediatria = new Departamento("Centro Pediátrico", EspecialidadMedica.PEDIATRIA);
    Departamento traumatologia = new Departamento("Traumatología Avanzada", EspecialidadMedica.TRAUMATOLOGIA);

        // Asignar departamentos al hospital
        hospital.agregarDepartamento(cardiologia);
        hospital.agregarDepartamento(pediatria);
        hospital.agregarDepartamento(traumatologia);

        // Crear salas por departamento
        crearSalasPorDepartamento(cardiologia, pediatria, traumatologia);

        System.out.println("Hospital inicializado con " + hospital.getDepartamentos().size() + " departamentos\n");
        return hospital;
    }

    private static void crearSalasPorDepartamento(Departamento cardiologia, Departamento pediatria, Departamento traumatologia) {
        // Salas de Cardiología
    cardiologia.crearSala("CARD-210", "Consultorio");
    cardiologia.crearSala("CARD-211", "Quirófano");

        // Salas de Pediatría
    pediatria.crearSala("PED-320", "Consultorio");

        // Salas de Traumatología
    traumatologia.crearSala("TRAUMA-415", "Emergencias");
    }

    private static List<Medico> crearMedicos(Hospital hospital) {
        System.out.println("Registrando médicos especialistas...");

        List<Medico> medicos = new ArrayList<>();

        // Crear médicos especialistas
    Medico cardiologo = new Medico("Gabriel", "Pérez", "45678901",
        LocalDate.of(1972, 11, 30), TipoSangre.A_POSITIVO,
        "MP-67890", EspecialidadMedica.CARDIOLOGIA);

    Medico pediatra = new Medico("Lucía", "Ramírez", "56789012",
        LocalDate.of(1983, 4, 18), TipoSangre.O_NEGATIVO,
        "MP-78901", EspecialidadMedica.PEDIATRIA);

    Medico traumatologo = new Medico("Javier", "Sosa", "67890123",
        LocalDate.of(1979, 9, 5), TipoSangre.B_POSITIVO,
        "MP-89012", EspecialidadMedica.TRAUMATOLOGIA);

        // Asignar médicos a sus departamentos correspondientes
        for (Departamento dep : hospital.getDepartamentos()) {
            switch (dep.getEspecialidad()) {
                case CARDIOLOGIA:
                    dep.agregarMedico(cardiologo);
                    medicos.add(cardiologo);
                    break;
                case PEDIATRIA:
                    dep.agregarMedico(pediatra);
                    medicos.add(pediatra);
                    break;
                case TRAUMATOLOGIA:
                    dep.agregarMedico(traumatologo);
                    medicos.add(traumatologo);
                    break;
            }
        }

        System.out.println("Registrados " + medicos.size() + " médicos especialistas\n");
        return medicos;
    }

    private static List<Paciente> registrarPacientes(Hospital hospital) {
        System.out.println("Registrando pacientes...");

        List<Paciente> pacientes = new ArrayList<>();

        // Crear pacientes con diferentes perfiles
    Paciente pacienteCardiaco = new Paciente("Sofía", "Morales", "44444444",
        LocalDate.of(1982, 2, 14), TipoSangre.A_POSITIVO,
        "011-4040-4040", "San Lorenzo 742");

    Paciente pacientePediatrico = new Paciente("Valentín", "Ortega", "55555555",
        LocalDate.of(2012, 11, 3), TipoSangre.O_POSITIVO,
        "011-5151-5151", "Pueyrredón 930");

    Paciente pacienteTraumatologico = new Paciente("Martín", "Quinteros", "66666666",
        LocalDate.of(1991, 7, 19), TipoSangre.AB_NEGATIVO,
        "011-6262-6262", "Los Patos 1550");

        // Registrar pacientes en el hospital
        hospital.agregarPaciente(pacienteCardiaco);
        hospital.agregarPaciente(pacientePediatrico);
        hospital.agregarPaciente(pacienteTraumatologico);

        pacientes.add(pacienteCardiaco);
        pacientes.add(pacientePediatrico);
        pacientes.add(pacienteTraumatologico);

        // Configurar historias clínicas
        configurarHistoriasClinicas(pacienteCardiaco, pacientePediatrico, pacienteTraumatologico);

        System.out.println("Registrados " + pacientes.size() + " pacientes con historias clínicas\n");
        return pacientes;
    }

    private static void configurarHistoriasClinicas(Paciente pacienteCardiaco, Paciente pacientePediatrico, Paciente pacienteTraumatologico) {
        // Historia clínica del paciente cardíaco
    pacienteCardiaco.getHistoriaClinica().agregarDiagnostico("Cardiopatía isquémica estable");
    pacienteCardiaco.getHistoriaClinica().agregarTratamiento("Losartán 50mg");
    pacienteCardiaco.getHistoriaClinica().agregarAlergia("Amoxicilina");

        // Historia clínica del paciente pediátrico
    pacientePediatrico.getHistoriaClinica().agregarDiagnostico("Control anual pediátrico");
    pacientePediatrico.getHistoriaClinica().agregarTratamiento("Refuerzo antigripal 2025");

        // Historia clínica del paciente traumatológico
    pacienteTraumatologico.getHistoriaClinica().agregarDiagnostico("Luxación de hombro en rehabilitación");
    pacienteTraumatologico.getHistoriaClinica().agregarTratamiento("Kinesiología intensiva y analgésicos");
    pacienteTraumatologico.getHistoriaClinica().agregarAlergia("Diclofenac");
    }

    // ===== GESTIÓN DE CITAS =====

    private static void programarCitas(CitaManager citaManager, List<Medico> medicos, List<Paciente> pacientes, Hospital hospital) throws CitaException {
        System.out.println("Programando citas médicas...");

        // Obtener salas por especialidad
        Map<EspecialidadMedica, Sala> salasPorEspecialidad = obtenerSalasPorEspecialidad(hospital);

        // Calcular fechas futuras (a partir de mañana)
        LocalDateTime fechaBase = LocalDateTime.now().plusDays(1);

        // Programar cita cardiológica
        Cita citaCardiologica = citaManager.programarCita(
                pacientes.get(0), // Paciente cardíaco
                obtenerMedicoPorEspecialidad(medicos, EspecialidadMedica.CARDIOLOGIA),
                salasPorEspecialidad.get(EspecialidadMedica.CARDIOLOGIA),
        fechaBase.withHour(9).withMinute(30),
        new BigDecimal("180000.00")
        );
    citaCardiologica.setObservaciones("Paciente con antecedentes de arritmia crónica");
        citaCardiologica.setEstado(EstadoCita.COMPLETADA);

        // Programar cita pediátrica
        Cita citaPediatrica = citaManager.programarCita(
                pacientes.get(1), // Paciente pediátrico
                obtenerMedicoPorEspecialidad(medicos, EspecialidadMedica.PEDIATRIA),
                salasPorEspecialidad.get(EspecialidadMedica.PEDIATRIA),
        fechaBase.plusDays(1).withHour(15).withMinute(15),
        new BigDecimal("95000.00")
        );
    citaPediatrica.setObservaciones("Chequeo anual pediátrico y control de crecimiento");
        citaPediatrica.setEstado(EstadoCita.EN_CURSO);

        // Programar cita traumatológica
        Cita citaTraumatologica = citaManager.programarCita(
                pacientes.get(2), // Paciente traumatológico
                obtenerMedicoPorEspecialidad(medicos, EspecialidadMedica.TRAUMATOLOGIA),
                salasPorEspecialidad.get(EspecialidadMedica.TRAUMATOLOGIA),
        fechaBase.plusDays(2).withHour(11).withMinute(0),
        new BigDecimal("130000.00")
        );
    citaTraumatologica.setObservaciones("Evaluación de rehabilitación postquirúrgica");

        System.out.println("Programadas 3 citas médicas exitosamente\n");
    }

    // ===== MÉTODOS AUXILIARES =====

    private static Map<EspecialidadMedica, Sala> obtenerSalasPorEspecialidad(Hospital hospital) {
        Map<EspecialidadMedica, Sala> salasPorEspecialidad = new HashMap<>();

        for (Departamento dep : hospital.getDepartamentos()) {
            if (!dep.getSalas().isEmpty()) {
                salasPorEspecialidad.put(dep.getEspecialidad(), dep.getSalas().get(0));
            }
        }

        return salasPorEspecialidad;
    }

    private static Medico obtenerMedicoPorEspecialidad(List<Medico> medicos, EspecialidadMedica especialidad) {
        return medicos.stream()
                .filter(medico -> medico.getEspecialidad() == especialidad)
                .findFirst()
                .orElse(null);
    }

    // ===== MÉTODOS DE VISUALIZACIÓN =====

    private static void mostrarInformacionCompleta(Hospital hospital, CitaManager citaManager) {
        mostrarInformacionHospital(hospital);
        mostrarDepartamentosYPersonal(hospital);
        mostrarPacientesEHistorias(hospital);
        mostrarCitasProgramadas(hospital, citaManager);
    }

    private static void mostrarInformacionHospital(Hospital hospital) {
        System.out.println("===== INFORMACIÓN DEL HOSPITAL =====");
        System.out.println(hospital);
        System.out.println("Departamentos: " + hospital.getDepartamentos().size());
        System.out.println("Pacientes registrados: " + hospital.getPacientes().size());
        System.out.println();
    }

    private static void mostrarDepartamentosYPersonal(Hospital hospital) {
        System.out.println("===== DEPARTAMENTOS Y PERSONAL =====");
        for (Departamento dep : hospital.getDepartamentos()) {
            System.out.println(dep);

            System.out.println("  Médicos (" + dep.getMedicos().size() + "):");
            for (Medico medico : dep.getMedicos()) {
                System.out.println("    " + medico);
            }

            System.out.println("  Salas (" + dep.getSalas().size() + "):");
            for (Sala sala : dep.getSalas()) {
                System.out.println("    " + sala);
            }
            System.out.println();
        }
    }

    private static void mostrarPacientesEHistorias(Hospital hospital) {
        System.out.println("===== PACIENTES E HISTORIAS CLÍNICAS =====");
        for (Paciente paciente : hospital.getPacientes()) {
            System.out.println(paciente);
            HistoriaClinica historia = paciente.getHistoriaClinica();
            System.out.println("  Historia: " + historia.getNumeroHistoria() + " | Edad: " + paciente.getEdad() + " años");

            if (!historia.getDiagnosticos().isEmpty()) {
                System.out.println("  Diagnósticos: " + historia.getDiagnosticos());
            }
            if (!historia.getTratamientos().isEmpty()) {
                System.out.println("  Tratamientos: " + historia.getTratamientos());
            }
            if (!historia.getAlergias().isEmpty()) {
                System.out.println("  Alergias: " + historia.getAlergias());
            }
            System.out.println();
        }
    }

    private static void mostrarCitasProgramadas(Hospital hospital, CitaManager citaManager) {
        System.out.println("===== CITAS PROGRAMADAS =====");

        // Mostrar citas por paciente
        for (Paciente paciente : hospital.getPacientes()) {
            List<Cita> citasPaciente = citaManager.getCitasPorPaciente(paciente);
            if (!citasPaciente.isEmpty()) {
                System.out.println("Citas de " + paciente.getNombreCompleto() + ":");
                for (Cita cita : citasPaciente) {
                    System.out.println("  " + cita);
                    if (!cita.getObservaciones().isEmpty()) {
                        System.out.println("    Observaciones: " + cita.getObservaciones());
                    }
                }
                System.out.println();
            }
        }
    }

    // ===== PERSISTENCIA DE DATOS =====

    private static void probarPersistencia(CitaManager citaManager, List<Paciente> pacientes, List<Medico> medicos, Hospital hospital) {
        System.out.println("===== PRUEBA DE PERSISTENCIA =====");

        try {
            // Guardar citas en archivo CSV
            String archivo = "citas_hospital.csv";
            citaManager.guardarCitas(archivo);
            System.out.println("✓ Citas guardadas en " + archivo);

            // Probar carga desde archivo
            CitaManager nuevoCitaManager = new CitaManager();
            Map<String, Paciente> pacientesMap = crearMapaPacientes(pacientes);
            Map<String, Medico> medicosMap = crearMapaMedicos(medicos);
            Map<String, Sala> salasMap = crearMapaSalas(hospital);

            nuevoCitaManager.cargarCitas(archivo, pacientesMap, medicosMap, salasMap);
            System.out.println("✓ Citas cargadas exitosamente desde archivo");

            // Verificar que se cargaron correctamente
            int totalCitasCargadas = 0;
            for (Paciente paciente : pacientes) {
                totalCitasCargadas += nuevoCitaManager.getCitasPorPaciente(paciente).size();
            }
            System.out.println("✓ Total de citas cargadas: " + totalCitasCargadas);

        } catch (Exception e) {
            System.err.println("✗ Error en persistencia: " + e.getMessage());
        }

        System.out.println();
    }

    private static Map<String, Paciente> crearMapaPacientes(List<Paciente> pacientes) {
        Map<String, Paciente> mapa = new HashMap<>();
        for (Paciente p : pacientes) {
            mapa.put(p.getDni(), p);
        }
        return mapa;
    }

    private static Map<String, Medico> crearMapaMedicos(List<Medico> medicos) {
        Map<String, Medico> mapa = new HashMap<>();
        for (Medico m : medicos) {
            mapa.put(m.getDni(), m);
        }
        return mapa;
    }

    private static Map<String, Sala> crearMapaSalas(Hospital hospital) {
        Map<String, Sala> mapa = new HashMap<>();
        for (Departamento dep : hospital.getDepartamentos()) {
            for (Sala sala : dep.getSalas()) {
                mapa.put(sala.getNumero(), sala);
            }
        }
        return mapa;
    }

    // ===== PRUEBAS DE VALIDACIÓN =====

    private static void ejecutarPruebasValidacion(CitaManager citaManager, List<Medico> medicos, List<Paciente> pacientes, Hospital hospital) {
        System.out.println("===== PRUEBAS DE VALIDACIÓN =====");

        Paciente pacientePrueba = pacientes.get(0);
        Medico medicoPrueba = medicos.get(0);
        Sala salaPrueba = hospital.getDepartamentos().get(0).getSalas().get(0);

        // Prueba 1: Cita en el pasado
        probarValidacionFechaPasado(citaManager, pacientePrueba, medicoPrueba, salaPrueba);

        // Prueba 2: Costo negativo
        probarValidacionCostoNegativo(citaManager, pacientePrueba, medicoPrueba, salaPrueba);

        // Prueba 3: Especialidad incompatible
        probarValidacionEspecialidadIncompatible(citaManager, pacientePrueba, medicos, hospital);

        System.out.println();
    }

    private static void probarValidacionFechaPasado(CitaManager citaManager, Paciente paciente, Medico medico, Sala sala) {
        try {
            citaManager.programarCita(paciente, medico, sala,
                    LocalDateTime.of(2020, 1, 1, 10, 0),
                    new BigDecimal("100000.00"));
            System.out.println("✗ ERROR: Se permitió cita en el pasado");
        } catch (CitaException e) {
            System.out.println("✓ Validación fecha pasado: " + e.getMessage());
        }
    }

    private static void probarValidacionCostoNegativo(CitaManager citaManager, Paciente paciente, Medico medico, Sala sala) {
        try {
            citaManager.programarCita(paciente, medico, sala,
                    LocalDateTime.of(2025, 3, 1, 10, 0),
                    new BigDecimal("-50000.00"));
            System.out.println("✗ ERROR: Se permitió costo negativo");
        } catch (CitaException e) {
            System.out.println("✓ Validación costo negativo: " + e.getMessage());
        }
    }

    private static void probarValidacionEspecialidadIncompatible(CitaManager citaManager, Paciente paciente, List<Medico> medicos, Hospital hospital) {
        try {
            // Intentar programar cardiólogo en sala de pediatría
            Medico cardiologo = obtenerMedicoPorEspecialidad(medicos, EspecialidadMedica.CARDIOLOGIA);
            Sala salaPediatria = obtenerSalaPorEspecialidad(hospital, EspecialidadMedica.PEDIATRIA);

            citaManager.programarCita(paciente, cardiologo, salaPediatria,
                    LocalDateTime.of(2025, 3, 1, 10, 0),
                    new BigDecimal("100000.00"));
            System.out.println("✗ ERROR: Se permitió especialidad incompatible");
        } catch (CitaException e) {
            System.out.println("✓ Validación especialidad incompatible: " + e.getMessage());
        }
    }

    private static Sala obtenerSalaPorEspecialidad(Hospital hospital, EspecialidadMedica especialidad) {
        return hospital.getDepartamentos().stream()
                .filter(dep -> dep.getEspecialidad() == especialidad)
                .flatMap(dep -> dep.getSalas().stream())
                .findFirst()
                .orElse(null);
    }

    // ===== ESTADÍSTICAS FINALES =====

    private static void mostrarEstadisticasFinales(Hospital hospital) {
        System.out.println("===== ESTADÍSTICAS FINALES =====");

        // Contadores generales
        int totalDepartamentos = hospital.getDepartamentos().size();
        int totalPacientes = hospital.getPacientes().size();
        int totalMedicos = hospital.getDepartamentos().stream()
                .mapToInt(dep -> dep.getMedicos().size())
                .sum();
        int totalSalas = hospital.getDepartamentos().stream()
                .mapToInt(dep -> dep.getSalas().size())
                .sum();

        System.out.println("Departamentos: " + totalDepartamentos);
        System.out.println("Médicos: " + totalMedicos);
        System.out.println("Salas: " + totalSalas);
        System.out.println("Pacientes: " + totalPacientes);

        // Distribución por tipo de sangre
        mostrarDistribucionTipoSangre(hospital);

        // Distribución por especialidad
        mostrarDistribucionEspecialidades(hospital);
    }

    private static void mostrarDistribucionTipoSangre(Hospital hospital) {
        System.out.println("\nDistribución por tipo de sangre:");
        Map<TipoSangre, Integer> distribucion = new HashMap<>();

        for (Paciente paciente : hospital.getPacientes()) {
            TipoSangre tipo = paciente.getTipoSangre();
            distribucion.put(tipo, distribucion.getOrDefault(tipo, 0) + 1);
        }

        distribucion.entrySet().stream()
                .sorted(Map.Entry.<TipoSangre, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("  " + entry.getKey().getDescripcion() + ": " + entry.getValue()));
    }

    private static void mostrarDistribucionEspecialidades(Hospital hospital) {
        System.out.println("\nDistribución por especialidad:");
        for (Departamento dep : hospital.getDepartamentos()) {
            System.out.println("  " + dep.getEspecialidad().getDescripcion() + ": " +
                    dep.getMedicos().size() + " médicos, " +
                    dep.getSalas().size() + " salas");
        }
    }
}