package entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Setter @Getter
@NoArgsConstructor
@Builder
@ToString(exclude = "sucursales")

public class Empresa {
    private Long id;
    private String nombre;
    private String razonSocial;
    private Long cuil;

    @Builder.Default
    private Set<Sucursal> sucursales = new HashSet<>();
}
