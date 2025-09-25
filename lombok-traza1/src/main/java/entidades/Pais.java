package entidades;

import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "provincias")
public class Pais {
    @EqualsAndHashCode.Include
    private Long id;
    private String nombre;

    @Builder.Default
    private Set<Provincia> provincias = new HashSet<>();

    public void addProvincia(Provincia p) {
        if (p == null) return;
        p.setPais(this);
        provincias.add(p);
    }
}
