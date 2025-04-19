package com.pe.cine_cultura.repository;

import com.pe.cine_cultura.model.Categoria;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CategoriaRepository {
    private final Map<Long, Categoria> categorias = new HashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    public Categoria guardar(Categoria categoria) {
        if(categoria.getIdCategoria() == null){
            Long id = secuencia.getAndIncrement();
            categoria.setIdCategoria(id);
            categorias.put(id, categoria);
        } else {
            categorias.put(categoria.getIdCategoria(), categoria);
        }
        return categoria;
    }

    public List<Categoria> listarTodas() {
        return new ArrayList<>(categorias.values());
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return Optional.ofNullable(categorias.get(id));
    }

    public List<Categoria> buscarPorEstado(Categoria.EstadoCategoria estado) {
        return categorias.values().stream()
                .filter(c -> c.getEstado() == estado)
                .toList();
    }

    public boolean eliminar(Long id) {
        return categorias.remove(id) != null;
    }

    public boolean existeCategoria(Long id) {
        return categorias.containsKey(id);
    }
}
