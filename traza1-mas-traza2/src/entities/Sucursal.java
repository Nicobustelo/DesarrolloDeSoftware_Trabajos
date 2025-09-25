package entities;
import lombok.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Setter @Getter @ToString
@NoArgsConstructor
@Builder

public class Sucursal {
    private Long id;
    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private boolean esCasaMatriz;
    private Empresa empresa;
    private Domicilio domicilio;

    @Builder.Default
    private Set<ArticuloManufacturado> articulosManufacturado = new HashSet<>();

    public void addArticuloManufacturado(ArticuloManufacturado articuloManufacturado){
        articulosManufacturado.add(articuloManufacturado);
    }

}
