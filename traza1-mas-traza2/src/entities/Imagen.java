package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@Builder@ToString
public class Imagen {
    private String name;
    private String url;
    private Long id;
    private Articulo articulo;
}
