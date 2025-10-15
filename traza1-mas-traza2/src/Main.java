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

        Categoria cat1 = Categoria.builder().id(1L).denominacion("Pastas").build();
        Categoria cat2 = Categoria.builder().id(2L).denominacion("Hamburguesas").build();
        Categoria cat3 = Categoria.builder().id(3L).denominacion("Milanesas").build();
        Categoria cat4 = Categoria.builder().id(4L).denominacion("Ingredientes").build();

        categoriaRepository.save(cat1);
        categoriaRepository.save(cat2);
        categoriaRepository.save(cat3);
        categoriaRepository.save(cat4);

        UnidadMedida unM1 = UnidadMedida.builder().id(1L).denominacion("Kilogramos").build();
        UnidadMedida unM2 = UnidadMedida.builder().id(2L).denominacion("Litros").build();
        UnidadMedida unM3 = UnidadMedida.builder().id(3L).denominacion("Unidades").build();

        unidadMedidaRepository.save(unM1);
        unidadMedidaRepository.save(unM2);
        unidadMedidaRepository.save(unM3);

        ArticuloInsumo sal = ArticuloInsumo.builder()
                .denominacion("Pimienta")
                .precioCompra(1.5)
                .stockActual(80)
                .stockMinimo(8)
                .stockMaximo(150)
                .esParaElaborar(true)
                .unidadMedida(unM3)
                .categoria(cat4)
                .build();

        ArticuloInsumo harina = ArticuloInsumo.builder()
                .denominacion("Avena")
                .precioCompra(0.8)
                .stockActual(60)
                .stockMinimo(6)
                .stockMaximo(120)
                .esParaElaborar(true)
                .unidadMedida(unM1)
                .categoria(cat4)
                .build();

        ArticuloInsumo aceite = ArticuloInsumo.builder()
                .denominacion("Manteca")
                .precioCompra(4.0)
                .stockActual(25)
                .stockMinimo(2)
                .stockMaximo(50)
                .esParaElaborar(true)
                .unidadMedida(unM2)
                .categoria(cat4)
                .build();

        ArticuloInsumo carne = ArticuloInsumo.builder()
                .denominacion("Pollo")
                .precioCompra(6.5)
                .stockActual(15)
                .stockMinimo(1)
                .stockMaximo(30)
                .esParaElaborar(true)
                .unidadMedida(unM1)
                .categoria(cat4)
                .build();

        articuloInsumoRepository.save(sal);
        articuloInsumoRepository.save(harina);
        articuloInsumoRepository.save(aceite);
        articuloInsumoRepository.save(carne);

        Imagen img1 = Imagen.builder().
                name("RavioliEspinaca1").url("http://example.com/pasta1").build();
        Imagen img2 = Imagen.builder().name("RavioliEspinaca2").url("http://example.com/pasta2").build();
        Imagen img3 = Imagen.builder().name("RavioliEspinaca3").url("http://example.com/pasta3").build();
        Imagen img4 = Imagen.builder().name("MilanesaNapolitana1").url("http://example.com/milanesa1").build();
        Imagen img5 = Imagen.builder().name("MilanesaNapolitana2").url("http://example.com/milanesa2").build();
        Imagen img6 = Imagen.builder().name("MilanesaNapolitana3").url("http://example.com/milanesa3").build();

        // Crear detalles de artículos manufacturados
        ArticuloManufacturadoDetalle detalle1PastasRavioli = ArticuloManufacturadoDetalle.builder()
                .cantidad(2)
                .insumo(sal)
                .build();

        ArticuloManufacturadoDetalle detalle2PastasRavioli = ArticuloManufacturadoDetalle.builder()
                .cantidad(3)
                .insumo(harina)
                .build();

        ArticuloManufacturadoDetalle detalle3PastasRavioli = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(aceite)
                .build();

        ArticuloManufacturadoDetalle detalle1MilanesaNapo = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(sal)
                .build();

        ArticuloManufacturadoDetalle detalle2MilanesaNapo = ArticuloManufacturadoDetalle.builder()
                .cantidad(2)
                .insumo(aceite)
                .build();

        ArticuloManufacturadoDetalle detalle3MilanesaNapo = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(carne)
                .build();

        // Crear artículos manufacturados
        ArticuloManufacturado ravioliEspinaca = ArticuloManufacturado.builder()
                .denominacion("Ravioli de Espinaca")
                .precioVenta(14.0)
                .descripcion("Ravioli rellenos de espinaca y ricotta")
                .tiempoEstimadoMinutos(25)
                .preparacion("Cocinar en agua hirviendo por 5 minutos")
                .categoria(cat1)
                .unidadMedida(unM1)
                .imagenes(new HashSet<>(Set.of(img1, img2, img3)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detalle1PastasRavioli, detalle2PastasRavioli, detalle3PastasRavioli)))
                .build();

        ArticuloManufacturado milanesaNapolitana = ArticuloManufacturado.builder()
                .denominacion("Milanesa Napolitana")
                .precioVenta(18.0)
                .descripcion("Milanesa napolitana con jamón y queso")
                .tiempoEstimadoMinutos(30)
                .preparacion("Freír la milanesa y gratinar al horno por 30 minutos")
                .categoria(cat3)
                .unidadMedida(unM1)
                .imagenes(new HashSet<>(Set.of(img4, img5, img6)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detalle1MilanesaNapo, detalle2MilanesaNapo, detalle3MilanesaNapo)))
                .build();

        articuloManufacturadoRepository.save(ravioliEspinaca);
        articuloManufacturadoRepository.save(milanesaNapolitana);

        // Imágenes adicionales
        Imagen img7 = Imagen.builder().name("TallarinesBoloñesa1").url("http://example.com/tallarines1").build();
        Imagen img8 = Imagen.builder().name("TallarinesBoloñesa2").url("http://example.com/tallarines2").build();

        Imagen img9 = Imagen.builder().name("HamburguesaCompleta1").url("http://example.com/hamburguesa1").build();
        Imagen img10 = Imagen.builder().name("HamburguesaCompleta2").url("http://example.com/hamburguesa2").build();

        Imagen img11 = Imagen.builder().name("TortaChocolate1").url("http://example.com/torta1").build();
        Imagen img12 = Imagen.builder().name("TortaChocolate2").url("http://example.com/torta2").build();

// Detalles para Tallarines a la Boloñesa
        ArticuloManufacturadoDetalle detalle1TallarinesBoloñesa = ArticuloManufacturadoDetalle.builder()
                .cantidad(3)
                .insumo(harina)
                .build();

        ArticuloManufacturadoDetalle detalle2TallarinesBoloñesa = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(aceite)
                .build();

        ArticuloManufacturadoDetalle detalle3TallarinesBoloñesa = ArticuloManufacturadoDetalle.builder()
                .cantidad(2)
                .insumo(sal)
                .build();

// Detalles para Hamburguesa Completa
        ArticuloManufacturadoDetalle detalle1HamburguesaCompleta = ArticuloManufacturadoDetalle.builder()
                .cantidad(2)
                .insumo(aceite)
                .build();

        ArticuloManufacturadoDetalle detalle2HamburguesaCompleta = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(carne) // simulando hamburguesa como carne
                .build();

        ArticuloManufacturadoDetalle detalle3HamburguesaCompleta = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(sal)
                .build();

// Detalles para Torta de Chocolate
        ArticuloManufacturadoDetalle detalle1TortaChocolate = ArticuloManufacturadoDetalle.builder()
                .cantidad(2)
                .insumo(harina)
                .build();

        ArticuloManufacturadoDetalle detalle2TortaChocolate = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(aceite)
                .build();

        ArticuloManufacturadoDetalle detalle3TortaChocolate = ArticuloManufacturadoDetalle.builder()
                .cantidad(1)
                .insumo(sal)
                .build();

// Artículo Manufacturado: Tallarines a la Boloñesa
        ArticuloManufacturado tallarinesBoloñesa = ArticuloManufacturado.builder()
                .denominacion("Tallarines a la Boloñesa")
                .precioVenta(13.5)
                .descripcion("Tallarines frescos con salsa boloñesa")
                .tiempoEstimadoMinutos(22)
                .preparacion("Cocinar los tallarines por 8 minutos y agregar salsa")
                .categoria(cat1)
                .unidadMedida(unM1)
                .imagenes(new HashSet<>(Set.of(img7, img8)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detalle1TallarinesBoloñesa, detalle2TallarinesBoloñesa, detalle3TallarinesBoloñesa)))
                .build();

// Artículo Manufacturado: Hamburguesa Completa
        ArticuloManufacturado hamburguesaCompleta = ArticuloManufacturado.builder()
                .denominacion("Hamburguesa Completa")
                .precioVenta(10.5)
                .descripcion("Hamburguesa con lechuga, tomate, cebolla y papas")
                .tiempoEstimadoMinutos(15)
                .preparacion("Cocinar la carne y armar con todos los ingredientes")
                .categoria(cat2)
                .unidadMedida(unM1)
                .imagenes(new HashSet<>(Set.of(img9, img10)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detalle1HamburguesaCompleta, detalle2HamburguesaCompleta, detalle3HamburguesaCompleta)))
                .build();

// Artículo Manufacturado: Torta de Chocolate
        ArticuloManufacturado tortaChocolate = ArticuloManufacturado.builder()
                .denominacion("Torta de Chocolate")
                .precioVenta(5.5)
                .descripcion("Torta de chocolate húmeda con cobertura")
                .tiempoEstimadoMinutos(18)
                .preparacion("Hornear por 45 minutos y decorar con chocolate")
                .categoria(cat3)
                .unidadMedida(unM1)
                .imagenes(new HashSet<>(Set.of(img11, img12)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detalle1TortaChocolate, detalle2TortaChocolate, detalle3TortaChocolate)))
                .build();

// Guardar en repositorio
        articuloManufacturadoRepository.save(tallarinesBoloñesa);
        articuloManufacturadoRepository.save(hamburguesaCompleta);
        articuloManufacturadoRepository.save(tortaChocolate);


        //      CREO EL PAIS
        Pais chile = Pais.builder()
                .nombre("Chile")
                .build();

//      SANTIAGO, LOCALIDADES Y DOMICILIOS
        Provincia santiago = Provincia.builder()
                .id(1L)
                .nombre("Santiago")
                .pais(chile)
                .build();

        Localidad lasCondes = Localidad.builder()
                .id(1L)
                .nombre("Las Condes")
                .provincia(santiago)
                .build();

        Localidad providencia = Localidad.builder()
                .id(2L)
                .nombre("Providencia")
                .provincia(santiago)
                .build();

        Domicilio domicilioLasCondes = Domicilio.builder()
                .id(1L)
                .localidad(lasCondes)
                .calle("Avenida Apoquindo")
                .numero(1245)
                .cp(7550000)
                .build();

        Domicilio domicilioProvidencia = Domicilio.builder()
                .id(2L)
                .localidad(providencia)
                .calle("Manuel Montt")
                .numero(890)
                .cp(7500000)
                .build();


//      VALPARAISO LOCALIDADES Y DOMICILIOS
        Provincia valparaiso = Provincia.builder()
                .id(2L)
                .nombre("Valparaíso")
                .pais(chile)
                .build();

        Localidad viñaDelMar = Localidad.builder()
                .id(3L)
                .nombre("Viña del Mar")
                .provincia(valparaiso)
                .build();

        Localidad valparaisoCapital = Localidad.builder()
                .id(4L)
                .nombre("Valparaíso Capital")
                .provincia(valparaiso)
                .build();

        Domicilio domicilioViñaDelMar = Domicilio.builder()
                .id(3L)
                .localidad(viñaDelMar)
                .calle("Avenida Libertad")
                .numero(3200)
                .cp(2520000)
                .build();

        Domicilio domicilioValparaiso = Domicilio.builder()
                .id(4L)
                .localidad(valparaisoCapital)
                .calle("Plaza Victoria")
                .numero(150)
                .cp(2340000)
                .build();

//      SUCURSALES

        Sucursal sucursal1 = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal 1 Centro")
                .horarioApertura(LocalTime.of(8, 0))
                .horarioCierre(LocalTime.of(20, 0))
                .esCasaMatriz(true)
                .domicilio(domicilioLasCondes)
                .build();

        Sucursal sucursal2 = Sucursal.builder()
                .id(2L)
                .nombre("Sucursal 2 Norte")
                .horarioApertura(LocalTime.of(9, 30))
                .horarioCierre(LocalTime.of(21, 0))
                .esCasaMatriz(false)
                .domicilio(domicilioProvidencia)
                .build();

        Sucursal sucursal3 = Sucursal.builder()
                .id(3L)
                .nombre("Sucursal 3 Costa")
                .horarioApertura(LocalTime.of(7, 0))
                .horarioCierre(LocalTime.of(22, 0))
                .esCasaMatriz(true)
                .domicilio(domicilioViñaDelMar)
                .build();

        Sucursal sucursal4 = Sucursal.builder()
                .id(4L)
                .nombre("Sucursal 4 Puerto")
                .horarioApertura(LocalTime.of(6, 0))
                .horarioCierre(LocalTime.of(18, 30))
                .esCasaMatriz(false)
                .domicilio(domicilioValparaiso)
                .build();

//      EMPRESAS

        Empresa empresa1 = Empresa.builder()
                .id(1L)
                .nombre("Empresa 1 Metropolitana")
                .razonSocial("Gastronomía del Centro S.A.")
                .cuil(76543210L)
                .sucursales(new HashSet<>(Set.of(sucursal1, sucursal2)))
                .build();

        Empresa empresa2 = Empresa.builder()
                .id(2L)
                .nombre("Empresa 2 Costera")
                .razonSocial("Sabores del Pacífico Ltda.")
                .cuil(98765432L)
                .sucursales(new HashSet<>(Set.of(sucursal3, sucursal4)))
                .build();

//      ASIGNO LAS EMPRESAS A LAS SUCURSALES
        sucursal1.setEmpresa(empresa1);
        sucursal2.setEmpresa(empresa1);
        sucursal3.setEmpresa(empresa2);
        sucursal4.setEmpresa(empresa2);

        // AGREGO ARTICULOS A CADA SUCURSAL
        sucursal1.addArticuloManufacturado(ravioliEspinaca);
        sucursal1.addArticuloManufacturado(milanesaNapolitana);
        sucursal1.addArticuloManufacturado(tortaChocolate);
        sucursal2.addArticuloManufacturado(hamburguesaCompleta);
        sucursal3.addArticuloManufacturado(ravioliEspinaca);
        sucursal2.addArticuloManufacturado(milanesaNapolitana);
        sucursal3.addArticuloManufacturado(tortaChocolate);
        sucursal4.addArticuloManufacturado(hamburguesaCompleta);

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