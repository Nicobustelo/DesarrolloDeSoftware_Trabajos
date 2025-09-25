import entities.*;
import repositories.InMemoryRepository;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        InMemoryRepository<Categoria> categoriaRepository = new InMemoryRepository<>();
        InMemoryRepository<ArticuloInsumo> articuloInsumoRepository = new InMemoryRepository<>();
        InMemoryRepository<ArticuloManufacturado> articuloManufacturadoRepository = new InMemoryRepository<>();
        InMemoryRepository<UnidadMedida> unidadMedidaRepository = new InMemoryRepository<>();

        Categoria cat1 = Categoria.builder().id(1L).denominacion("Pizzas").build();
        Categoria cat2 = Categoria.builder().id(2L).denominacion("Sandwichs").build();
        Categoria cat3 = Categoria.builder().id(3L).denominacion("Lomos").build();
        Categoria cat4 = Categoria.builder().id(4L).denominacion("Insumos").build();

        categoriaRepository.save(cat1);
        categoriaRepository.save(cat2);
        categoriaRepository.save(cat3);
        categoriaRepository.save(cat4);

        UnidadMedida unM1 = UnidadMedida.builder().id(1L).denominacion("Kilogramos").build();
        UnidadMedida unM2 = UnidadMedida.builder().id(2L).denominacion("Litros").build();
        UnidadMedida unM3 = UnidadMedida.builder().id(3L).denominacion("Gramos").build();

        unidadMedidaRepository.save(unM1);
        unidadMedidaRepository.save(unM2);
        unidadMedidaRepository.save(unM3);

        ArticuloInsumo sal = ArticuloInsumo.builder()
                .denominacion("Sal")
                .precioCompra(1.0)
                .stockActual(100)
                .stockMinimo(10)
                .stockMaximo(200)
                .esParaElaborar(true)
                .unidadMedida(unM3)
                .categoria(cat4)
                .build();

        ArticuloInsumo harina = ArticuloInsumo.builder()
                .denominacion("Harina")
                .precioCompra(0.5)
                .stockActual(50)
                .stockMinimo(5)
                .stockMaximo(100)
                .esParaElaborar(true)
                .unidadMedida(unM1)
                .categoria(cat4)
                .build();

        ArticuloInsumo aceite = ArticuloInsumo.builder()
                .denominacion("Aceite")
                .precioCompra(3.0)
                .stockActual(30)
                .stockMinimo(3)
                .stockMaximo(60)
                .esParaElaborar(true)
                .unidadMedida(unM2)
                .categoria(cat4)
                .build();

        ArticuloInsumo carne = ArticuloInsumo.builder()
                .denominacion("Carne")
                .precioCompra(5.0)
                .stockActual(20)
                .stockMinimo(2)
                .stockMaximo(40)
                .esParaElaborar(true)
                .unidadMedida(unM1)
                .categoria(cat4)
                .build();

        articuloInsumoRepository.save(sal);
        articuloInsumoRepository.save(harina);
        articuloInsumoRepository.save(aceite);
        articuloInsumoRepository.save(carne);

        Imagen img1 = Imagen.builder().
                name("HawaianaPizza1").url("http://example.com/pizza1").build();
        Imagen img2 = Imagen.builder().name("HawaianaPizza2").url("http://example.com/pizza2").build();
        Imagen img3 = Imagen.builder().name("HawaianaPizza3").url("http://example.com/pizza3").build();
        Imagen img4 = Imagen.builder().name("LomoCompletoLomo1").url("http://example.com/lomo1").build();
        Imagen img5 = Imagen.builder().name("LomoCompletoLomo2").url("http://example.com/lomo2").build();
        Imagen img6 = Imagen.builder().name("LomoCompletoLomo3").url("http://example.com/lomo3").build();

        // Crear detalles de artículos manufacturados
        ArticuloManufacturadoDetalle detalle1PizzaHawaina = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(sal)
                .build();

        ArticuloManufacturadoDetalle detalle2PizzaHawaina = ArticuloManufacturadoDetalle.builder()
                .cantidad(2)
                .insumo(harina)
                .build();

        ArticuloManufacturadoDetalle detalle3PizzaHawaina = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(aceite)
                .build();

        ArticuloManufacturadoDetalle detalle1LomoCompleto = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(sal)
                .build();

        ArticuloManufacturadoDetalle detalle2LomoCompleto = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(aceite)
                .build();

        ArticuloManufacturadoDetalle detalle3LomoCompleto = ArticuloManufacturadoDetalle.builder()
                .cantidad(2)
                .insumo(carne)
                .build();

        // Crear artículos manufacturados
        ArticuloManufacturado pizzaHawaina = ArticuloManufacturado.builder()
                .denominacion("Pizza Hawaina")
                .precioVenta(12.0)
                .descripcion("Pizza con piña y jamón")
                .tiempoEstimadoMinutos(20)
                .preparacion("Hornear por 20 minutos")
                .categoria(cat1)
                .unidadMedida(unM1)
                .imagenes(new HashSet<>(Set.of(img1, img2, img3)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detalle1PizzaHawaina, detalle2PizzaHawaina, detalle3PizzaHawaina)))
                .build();

        ArticuloManufacturado lomoCompleto = ArticuloManufacturado.builder()
                .denominacion("Lomo Completo")
                .precioVenta(15.0)
                .descripcion("Lomo completo con todos los ingredientes")
                .tiempoEstimadoMinutos(25)
                .preparacion("Cocinar a la parrilla por 25 minutos")
                .categoria(cat3)
                .unidadMedida(unM1)
                .imagenes(new HashSet<>(Set.of(img4, img5, img6)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detalle1LomoCompleto, detalle2LomoCompleto, detalle3LomoCompleto)))
                .build();

        articuloManufacturadoRepository.save(pizzaHawaina);
        articuloManufacturadoRepository.save(lomoCompleto);

        // Imágenes adicionales
        Imagen img7 = Imagen.builder().name("PizzaMargarita1").url("http://example.com/margarita1").build();
        Imagen img8 = Imagen.builder().name("PizzaMargarita2").url("http://example.com/margarita2").build();

        Imagen img9 = Imagen.builder().name("SandwichPollo1").url("http://example.com/sandwich1").build();
        Imagen img10 = Imagen.builder().name("SandwichPollo2").url("http://example.com/sandwich2").build();

        Imagen img11 = Imagen.builder().name("EmpanadaCarne1").url("http://example.com/empanada1").build();
        Imagen img12 = Imagen.builder().name("EmpanadaCarne2").url("http://example.com/empanada2").build();

// Detalles para Pizza Margarita
        ArticuloManufacturadoDetalle detalle1PizzaMargarita = ArticuloManufacturadoDetalle.builder()
                .cantidad(2)
                .insumo(harina)
                .build();

        ArticuloManufacturadoDetalle detalle2PizzaMargarita = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(aceite)
                .build();

        ArticuloManufacturadoDetalle detalle3PizzaMargarita = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(sal)
                .build();

// Detalles para Sándwich de Pollo
        ArticuloManufacturadoDetalle detalle1SandwichPollo = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(aceite)
                .build();

        ArticuloManufacturadoDetalle detalle2SandwichPollo = ArticuloManufacturadoDetalle.builder()
                .cantidad(2)
                .insumo(carne) // simulando pollo como carne
                .build();

        ArticuloManufacturadoDetalle detalle3SandwichPollo = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(sal)
                .build();

// Detalles para Empanada de Carne
        ArticuloManufacturadoDetalle detalle1EmpanadaCarne = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(harina)
                .build();

        ArticuloManufacturadoDetalle detalle2EmpanadaCarne = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(carne)
                .build();

        ArticuloManufacturadoDetalle detalle3EmpanadaCarne = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(sal)
                .build();

// Artículo Manufacturado: Pizza Margarita
        ArticuloManufacturado pizzaMargarita = ArticuloManufacturado.builder()
                .denominacion("Pizza Margarita")
                .precioVenta(11.0)
                .descripcion("Pizza clásica con queso y tomate")
                .tiempoEstimadoMinutos(18)
                .preparacion("Hornear por 18 minutos")
                .categoria(cat1)
                .unidadMedida(unM1)
                .imagenes(new HashSet<>(Set.of(img7, img8)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detalle1PizzaMargarita, detalle2PizzaMargarita, detalle3PizzaMargarita)))
                .build();

// Artículo Manufacturado: Sándwich de Pollo
        ArticuloManufacturado sandwichPollo = ArticuloManufacturado.builder()
                .denominacion("Sándwich de Pollo")
                .precioVenta(9.0)
                .descripcion("Sándwich con pollo, lechuga y tomate")
                .tiempoEstimadoMinutos(10)
                .preparacion("Armar el sándwich con pan, pollo y vegetales")
                .categoria(cat2)
                .unidadMedida(unM1)
                .imagenes(new HashSet<>(Set.of(img9, img10)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detalle1SandwichPollo, detalle2SandwichPollo, detalle3SandwichPollo)))
                .build();

// Artículo Manufacturado: Empanada de Carne
        ArticuloManufacturado empanadaCarne = ArticuloManufacturado.builder()
                .denominacion("Empanada de Carne")
                .precioVenta(4.0)
                .descripcion("Empanada rellena con carne picada y SIN aceitunas")
                .tiempoEstimadoMinutos(12)
                .preparacion("Freír o hornear por 12 minutos")
                .categoria(cat3)
                .unidadMedida(unM1)
                .imagenes(new HashSet<>(Set.of(img11, img12)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detalle1EmpanadaCarne, detalle2EmpanadaCarne, detalle3EmpanadaCarne)))
                .build();

// Guardar en repositorio
        articuloManufacturadoRepository.save(pizzaMargarita);
        articuloManufacturadoRepository.save(sandwichPollo);
        articuloManufacturadoRepository.save(empanadaCarne);


        //      CREO EL PAIS
        Pais argentina = Pais.builder()
                .nombre("Argentina")
                .build();

//      BUENOS AIRES, LOCALIDADES Y DOMICILIOS
        Provincia buenosAires = Provincia.builder()
                .id(1L)
                .nombre("Buenos Aires")
                .pais(argentina)
                .build();

        Localidad caba = Localidad.builder()
                .id(1L)
                .nombre("CABA")
                .provincia(buenosAires)
                .build();

        Localidad laPlata = Localidad.builder()
                .id(2L)
                .nombre("laPlata")
                .provincia(buenosAires)
                .build();

        Domicilio domicilioCABA = Domicilio.builder()
                .id(1L)
                .localidad(caba)
                .calle("General Paz")
                .numero(912)
                .cp(5515)
                .build();

        Domicilio domicilioLaPlata = Domicilio.builder()
                .id(2L)
                .localidad(laPlata)
                .calle("Avenida Fernando Zuqui")
                .numero(102)
                .cp(5125)
                .build();


//      CORDOBA LOCALIDADES Y DOMICILIOS
        Provincia cordoba = Provincia.builder()
                .id(2L)
                .nombre("Cordoba")
                .pais(argentina)
                .build();

        Localidad cordobaCapital = Localidad.builder()
                .id(3L)
                .nombre("Cordoba Capital")
                .provincia(cordoba)
                .build();

        Localidad carlosPaz = Localidad.builder()
                .id(4L)
                .nombre("Villa Carlos Paz")
                .provincia(cordoba)
                .build();

        Domicilio domicilioCordobaCap = Domicilio.builder()
                .id(3L)
                .localidad(cordobaCapital)
                .calle("Avenida Fernet")
                .numero(7030)
                .cp(1921)
                .build();

        Domicilio domicilioCarlosPaz = Domicilio.builder()
                .id(4L)
                .localidad(carlosPaz)
                .calle("Avenida Cuarteto")
                .numero(5050)
                .cp(1121)
                .build();

//      SUCURSALES

        Sucursal sucursal1 = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal 1 Porteñito")
                .horarioApertura(LocalTime.of(9, 0))
                .horarioCierre(LocalTime.of(18, 0))
                .esCasaMatriz(true)
                .domicilio(domicilioCABA)
                .build();

        Sucursal sucursal2 = Sucursal.builder()
                .id(2L)
                .nombre("Sucursal 2 Bidonazo")
                .horarioApertura(LocalTime.of(10, 0))
                .horarioCierre(LocalTime.of(19, 0))
                .esCasaMatriz(true)
                .domicilio(domicilioLaPlata)
                .build();

        Sucursal sucursal3 = Sucursal.builder()
                .id(3L)
                .nombre("Sucursal 3 Fernecito")
                .horarioApertura(LocalTime.of(3, 0))
                .horarioCierre(LocalTime.of(7, 0))
                .esCasaMatriz(false)
                .domicilio(domicilioCordobaCap)
                .build();

        Sucursal sucursal4 = Sucursal.builder()
                .id(4L)
                .nombre("Sucursal 4 Cuartetazo")
                .horarioApertura(LocalTime.of(2, 0))
                .horarioCierre(LocalTime.of(23, 0))
                .esCasaMatriz(true)
                .domicilio(domicilioCarlosPaz)
                .build();

//      EMPRESAS

        Empresa empresa1 = Empresa.builder()
                .id(1L)
                .nombre("Empresa 1 Sho")
                .razonSocial("Sho soy porteño")
                .cuil(1020304050L)
                .sucursales(new HashSet<>(Set.of(sucursal1, sucursal2)))
                .build();

        Empresa empresa2 = Empresa.builder()
                .id(2L)
                .nombre("Empresa 2 ferneteros")
                .razonSocial("Siempre 70/30")
                .cuil(70307030L)
                .sucursales(new HashSet<>(Set.of(sucursal3, sucursal4)))
                .build();

//      ASIGNO LAS EMPRESAS A LAS SUCURSALES
        sucursal1.setEmpresa(empresa1);
        sucursal2.setEmpresa(empresa1);
        sucursal3.setEmpresa(empresa2);
        sucursal4.setEmpresa(empresa2);

        // AGREGO ARTICULOS A CADA SUCURSAL
        sucursal1.addArticuloManufacturado(pizzaHawaina);
        sucursal1.addArticuloManufacturado(lomoCompleto);
        sucursal1.addArticuloManufacturado(empanadaCarne);
        sucursal2.addArticuloManufacturado(sandwichPollo);
        sucursal3.addArticuloManufacturado(pizzaHawaina);
        sucursal2.addArticuloManufacturado(lomoCompleto);
        sucursal3.addArticuloManufacturado(empanadaCarne);
        sucursal4.addArticuloManufacturado(sandwichPollo);

//      CREO EL inMemoryRepository
        InMemoryRepository<Empresa> empresasRepository = new InMemoryRepository<>();

        System.out.println("GUARDANDO EMPRESAS");
        empresasRepository.save(empresa1);
        empresasRepository.save(empresa2);

        // Mostrar todas las empresas
        System.out.println("Todas las empresas:");
        List<Empresa> todasLasEmpresas = empresasRepository.findAll();
        todasLasEmpresas.forEach(empresa -> {
            System.out.println("\nEmpresa: "+ empresa.getNombre());
            Set<Sucursal> sucursales = empresa.getSucursales();
            sucursales.forEach(sucursal -> {
                System.out.println("\nSucursal: " + sucursal.getNombre());
                Set<ArticuloManufacturado> articulos = sucursal.getArticulosManufacturado();
                articulos.forEach(articulo -> {
                    System.out.println("Articulo: "+ articulo.getDescripcion()+" $" + articulo.getPrecioVenta());
                });
            });
        });




    }
}