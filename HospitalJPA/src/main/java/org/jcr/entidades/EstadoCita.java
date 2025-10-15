package org.jcr.entidades;
import lombok.*;

@Getter
@RequiredArgsConstructor
public enum EstadoCita {
    PROGRAMADA("Programada"),
    EN_CURSO("En Curso"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada"),
    NO_ASISTIO("No Asistió");

    private final String descripcion;
    public String getDescripcion() {
        return descripcion;
    }
}
