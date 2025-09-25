package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@Builder@ToString
public class UnidadMedida {
    private Long id;
    private String denominacion;
}
