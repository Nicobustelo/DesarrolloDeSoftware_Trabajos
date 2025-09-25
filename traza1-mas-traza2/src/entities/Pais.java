package entities;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder

public class Pais {
    private Long id;
    private String nombre;
}
