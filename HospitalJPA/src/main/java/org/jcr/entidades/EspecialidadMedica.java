package org.jcr.entidades;
import lombok.*;

@Getter
@RequiredArgsConstructor
public enum EspecialidadMedica {
    CARDIOLOGIA("Cardiología"),
    NEUROLOGIA("Neurología"),
    PEDIATRIA("Pediatría"),
    TRAUMATOLOGIA("Traumatología"),
    GINECOLOGIA("Ginecología"),
    UROLOGIA("Urología"),
    OFTALMOLOGIA("Oftalmología"),
    DERMATOLOGIA("Dermatología"),
    PSIQUIATRIA("Psiquiatría"),
    MEDICINA_GENERAL("Medicina General"),
    CIRUGIA_GENERAL("Cirugía General"),
    ANESTESIOLOGIA("Anestesiología");

    private final String descripcion;

    public String getDescripcion() {
        return descripcion;
    }
}
