package entidades;

import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"provincia","domicilios"})
public class Localidad {
    @EqualsAndHashCode.Include
    private Long id;
    private String nombre;
    private Provincia provincia;

    @Builder.Default
    private Set<Domicilio> domicilios = new HashSet<>();

    public void addDomicilio(Domicilio d) {
        if (d == null) return;
        d.setLocalidad(this);
        domicilios.add(d);
    }
}
