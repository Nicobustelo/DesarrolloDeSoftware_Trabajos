package entidades;

import repository.EmpresaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // 1) País
        Pais argentina = Pais.builder()
                .id(10L).nombre("Argentina")
                .build();

        // 2) Provincia BA
        Provincia bsas = Provincia.builder()
                .id(20L).nombre("Buenos Aires")
                .pais(argentina)
                .build();
        argentina.getProvincias().add(bsas);

        Localidad caba = Localidad.builder()
                .id(100L).nombre("Capital Federal")
                .provincia(bsas)
                .build();
        bsas.getLocalidades().add(caba);

        Domicilio domCaba = Domicilio.builder()
                .id(200L).calle("Av. Santa Fe").numero(1500).cp(1010)
                .localidad(caba)
                .build();
        caba.getDomicilios().add(domCaba);

        Localidad moron = Localidad.builder()
                .id(110L).nombre("Morón")
                .provincia(bsas)
                .build();
        bsas.getLocalidades().add(moron);

        Domicilio domMoron = Domicilio.builder()
                .id(210L).calle("Belgrano").numero(345).cp(1708)
                .localidad(moron)
                .build();
        moron.getDomicilios().add(domMoron);

        // 3) Provincia Córdoba
        Provincia cordoba = Provincia.builder()
                .id(30L).nombre("Córdoba")
                .pais(argentina)
                .build();
        argentina.getProvincias().add(cordoba);

        Localidad cordobaCapital = Localidad.builder()
                .id(120L).nombre("Córdoba Centro")
                .provincia(cordoba)
                .build();
        cordoba.getLocalidades().add(cordobaCapital);

        Domicilio domCbaCap = Domicilio.builder()
                .id(220L).calle("Bv. Illia").numero(999).cp(5010)
                .localidad(cordobaCapital)
                .build();
        cordobaCapital.getDomicilios().add(domCbaCap);

        Localidad rioCuarto = Localidad.builder()
                .id(130L).nombre("Río Cuarto")
                .provincia(cordoba)
                .build();
        cordoba.getLocalidades().add(rioCuarto);

        Domicilio domRioCuarto = Domicilio.builder()
                .id(230L).calle("San Martín").numero(567).cp(5800)
                .localidad(rioCuarto)
                .build();
        rioCuarto.getDomicilios().add(domRioCuarto);

        // 4) Sucursales
        Sucursal suc1 = Sucursal.builder()
                .id(300L).nombre("Sucursal Capital Federal")
                .horarioApertura(LocalTime.of(8,30))
                .horarioCierre(LocalTime.of(17,30))
                .domicilio(domCaba)
                .build();

        Sucursal suc2 = Sucursal.builder()
                .id(301L).nombre("Sucursal Morón")
                .horarioApertura(LocalTime.of(9,30))
                .horarioCierre(LocalTime.of(19,0))
                .domicilio(domMoron)
                .build();

        Sucursal suc3 = Sucursal.builder()
                .id(302L).nombre("Sucursal Córdoba Centro")
                .horarioApertura(LocalTime.of(8,0))
                .horarioCierre(LocalTime.of(16,30))
                .domicilio(domCbaCap)
                .build();

        Sucursal suc4 = Sucursal.builder()
                .id(303L).nombre("Sucursal Río Cuarto")
                .horarioApertura(LocalTime.of(10,0))
                .horarioCierre(LocalTime.of(18,30))
                .domicilio(domRioCuarto)
                .build();

        // 5) Empresas
        Empresa emp1 = Empresa.builder()
                .nombre("TechCorp")
                .razonSocial("TechCorp Solutions S.A.")
                .cuit("30-87654321-0")
                .build();
        emp1.getSucursales().add(suc1);
        emp1.getSucursales().add(suc2);

        Empresa emp2 = Empresa.builder()
                .nombre("DataSoft")
                .razonSocial("DataSoft Innovaciones S.R.L.")
                .cuit("20-12344321-9")
                .build();
        emp2.getSucursales().add(suc3);
        emp2.getSucursales().add(suc4);

        // 6) CRUD
        EmpresaRepository repo = new EmpresaRepository();
        repo.save(emp1);
        repo.save(emp2);

        System.out.println("=== LISTADO COMPLETO DE EMPRESAS ===");
        List<Empresa> todas = repo.findAll();
        todas.forEach(System.out::println);

        System.out.println("\n=== BUSCAR POR ID (10) ===");
        Optional<Empresa> byId = repo.findById(10L);
        System.out.println(byId.orElse(null));

        System.out.println("\n=== BUSCAR POR NOMBRE 'Data' ===");
        repo.findByNombre("Data").forEach(System.out::println);

        System.out.println("\n=== ACTUALIZAR CUIT DE EMPRESA ID 2 ===");
        repo.updateCuitById(2L, "20-22222222-2");
        repo.findById(2L).ifPresent(System.out::println);

        System.out.println("\n=== ELIMINAR EMPRESA ID 1 ===");
        repo.deleteById(1L);
        repo.findAll().forEach(System.out::println);
    }
}
