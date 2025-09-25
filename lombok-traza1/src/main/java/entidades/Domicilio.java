package entidades;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "localidad")
public class Domicilio {
    @EqualsAndHashCode.Include
    private Long id;
    private String calle;
    private Integer numero;
    private Integer cp;
    private Localidad localidad;
}
