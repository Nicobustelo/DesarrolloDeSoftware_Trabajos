package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter @Setter

public class Categoria {
    private Long id;
    private String denominacion;

    @Builder.Default
    private Set<Articulo> articulos = new HashSet<>();

}
