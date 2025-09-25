package entities;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder

public class Provincia {
    private Long id;
    private String nombre;
    private Pais pais;
}
