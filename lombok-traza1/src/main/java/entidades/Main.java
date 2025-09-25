package entidades;

import repository.EmpresaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // 1) País
        Pais argentina = Pais.builder()
                .id(1L).nombre("Argentina")
                .build();

        // 2) Provincia BA
        Provincia bsas = Provincia.builder()
                .id(1L).nombre("Buenos Aires")
                .pais(argentina)
                .build();
        argentina.getProvincias().add(bsas);

        Localidad caba = Localidad.builder()
                .id(1L).nombre("CABA")
                .provincia(bsas)
                .build();
        bsas.getLocalidades().add(caba);

        Domicilio domCaba = Domicilio.builder()
                .id(1L).calle("Av. Corrientes").numero(1234).cp(1043)
                .localidad(caba)
                .build();
        caba.getDomicilios().add(domCaba);

        Localidad laPlata = Localidad.builder()
                .id(2L).nombre("La Plata")
                .provincia(bsas)
                .build();
        bsas.getLocalidades().add(laPlata);

        Domicilio domLaPlata = Domicilio.builder()
                .id(2L).calle("Calle 7").numero(456).cp(1900)
                .localidad(laPlata)
                .build();
        laPlata.getDomicilios().add(domLaPlata);

        // 3) Provincia Córdoba
        Provincia cordoba = Provincia.builder()
                .id(2L).nombre("Córdoba")
                .pais(argentina)
                .build();
        argentina.getProvincias().add(cordoba);

        Localidad cordobaCapital = Localidad.builder()
                .id(3L).nombre("Córdoba Capital")
                .provincia(cordoba)
                .build();
        cordoba.getLocalidades().add(cordobaCapital);

        Domicilio domCbaCap = Domicilio.builder()
                .id(3L).calle("Av. Colón").numero(789).cp(5000)
                .localidad(cordobaCapital)
                .build();
        cordobaCapital.getDomicilios().add(domCbaCap);

        Localidad villaCarlosPaz = Localidad.builder()
                .id(4L).nombre("Villa Carlos Paz")
                .provincia(cordoba)
                .build();
        cordoba.getLocalidades().add(villaCarlosPaz);

        Domicilio domVcp = Domicilio.builder()
                .id(4L).calle("Av. San Martín").numero(321).cp(5152)
                .localidad(villaCarlosPaz)
                .build();
        villaCarlosPaz.getDomicilios().add(domVcp);

        // 4) Sucursales
        Sucursal suc1 = Sucursal.builder()
                .id(1L).nombre("Sucursal CABA")
                .horarioApertura(LocalTime.of(9,0))
                .horarioCierre(LocalTime.of(18,0))
                .domicilio(domCaba)
                .build();

        Sucursal suc2 = Sucursal.builder()
                .id(2L).nombre("Sucursal La Plata")
                .horarioApertura(LocalTime.of(9,0))
                .horarioCierre(LocalTime.of(18,0))
                .domicilio(domLaPlata)
                .build();

        Sucursal suc3 = Sucursal.builder()
                .id(3L).nombre("Sucursal Córdoba Capital")
                .horarioApertura(LocalTime.of(9,0))
                .horarioCierre(LocalTime.of(18,0))
                .domicilio(domCbaCap)
                .build();

        Sucursal suc4 = Sucursal.builder()
                .id(4L).nombre("Sucursal Villa Carlos Paz")
                .horarioApertura(LocalTime.of(9,0))
                .horarioCierre(LocalTime.of(18,0))
                .domicilio(domVcp)
                .build();

        // 5) Empresas
        Empresa emp1 = Empresa.builder()
                .nombre("Empresa1")
                .razonSocial("Empresa Uno S.A.")
                .cuit("30-12345678-9")
                .build();
        emp1.getSucursales().add(suc1);
        emp1.getSucursales().add(suc2);

        Empresa emp2 = Empresa.builder()
                .nombre("Empresa2")
                .razonSocial("Empresa Dos S.R.L.")
                .cuit("20-98765432-1")
                .build();
        emp2.getSucursales().add(suc3);
        emp2.getSucursales().add(suc4);

        // 6) CRUD
        repository.EmpresaRepository repo = new repository.EmpresaRepository();
        repo.save(emp1);
        repo.save(emp2);

        System.out.println("=== TODAS ===");
        List<Empresa> todas = repo.findAll();
        todas.forEach(System.out::println);

        System.out.println("\n=== POR ID (1) ===");
        Optional<Empresa> byId = repo.findById(1L);
        System.out.println(byId.orElse(null));

        System.out.println("\n=== POR NOMBRE 'presa2' ===");
        repo.findByNombre("presa2").forEach(System.out::println);

        System.out.println("\n=== UPDATE CUIT ID 2 ===");
        repo.updateCuitById(2L, "20-11111111-1");
        repo.findById(2L).ifPresent(System.out::println);

        System.out.println("\n=== DELETE ID 1 ===");
        repo.deleteById(1L);
        repo.findAll().forEach(System.out::println);
    }
}
