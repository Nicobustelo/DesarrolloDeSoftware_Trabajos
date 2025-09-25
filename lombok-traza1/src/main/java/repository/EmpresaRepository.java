package repository;

import entidades.Empresa;

import java.util.*;
import java.util.stream.Collectors;

public class EmpresaRepository {
    private final Map<Long, Empresa> store = new HashMap<>();
    private long sequence = 1L;

    public Empresa save(Empresa e) {
        if (e.getId() == null) e.setId(sequence++);
        store.put(e.getId(), e);
        return e;
    }

    public Optional<Empresa> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Empresa> findAll() {
        return new ArrayList<>(store.values());
    }

    public List<Empresa> findByNombre(String nombre) {
        if (nombre == null) return List.of();
        String needle = nombre.toLowerCase();
        return store.values().stream()
                .filter(e -> e.getNombre() != null && e.getNombre().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }

    public boolean updateCuitById(Long id, String nuevoCuit) {
        Empresa e = store.get(id);
        if (e == null) return false;
        e.setCuit(nuevoCuit);
        return true;
    }

    public boolean deleteById(Long id) {
        return store.remove(id) != null;
    }
}
