package entidades;

import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"pais","localidades"})
public class Provincia {
    @EqualsAndHashCode.Include
    private Long id;
    private String nombre;
    private Pais pais;

    @Builder.Default
    private Set<Localidad> localidades = new HashSet<>();

    public void addLocalidad(Localidad l) {
        if (l == null) return;
        l.setProvincia(this);
        localidades.add(l);
    }
}
