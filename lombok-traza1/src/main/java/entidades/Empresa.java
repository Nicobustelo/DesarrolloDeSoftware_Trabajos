package entidades;

import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "sucursales")
public class Empresa {
    @EqualsAndHashCode.Include
    private Long id;
    private String nombre;
    private String razonSocial;
    private String cuit;
    private String logo;

    @Builder.Default
    private Set<Sucursal> sucursales = new HashSet<>();

    public void addSucursal(Sucursal s) {
        if (s != null) sucursales.add(s);
    }
}
