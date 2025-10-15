package org.jcr.entidades;
import lombok.*;

@Getter
@RequiredArgsConstructor
public enum TipoSangre {
    A_POSITIVO("A+"),
    A_NEGATIVO("A-"),
    B_POSITIVO("B+"),
    B_NEGATIVO("B-"),
    AB_POSITIVO("AB+"),
    AB_NEGATIVO("AB-"),
    O_POSITIVO("O+"),
    O_NEGATIVO("O-");

    private final String descripcion;

    public String getDescripcion() {
        return descripcion;
    }
}
