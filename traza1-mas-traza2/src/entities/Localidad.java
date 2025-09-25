package entities;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder

public class Localidad {
    private Long id;
    private String nombre;
    private Provincia provincia;
}
