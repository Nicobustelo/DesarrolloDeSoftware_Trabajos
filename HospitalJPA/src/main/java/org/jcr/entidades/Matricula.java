package org.jcr.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Matricula {

    @NotBlank(message = "La matrícula es obligatoria")
    @Pattern(regexp = "^MP-\\d{6}$", message = "Formato de matrícula inválido (ej: MP-123456)")
    @Column(name = "numeroMatricula", nullable = false, unique = true)
    private String numero;

}
