package com.pe.cine_cultura.service;

import com.pe.cine_cultura.model.Categoria;
import com.pe.cine_cultura.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.guardar(categoria);
    }

    public List<Categoria> obtenerTodasCategorias() {
        return categoriaRepository.listarTodas();
    }

    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.buscarPorId(id);
    }

    public List<Categoria> obtenerCategoriasPorEstado(Categoria.EstadoCategoria estado) {
        return categoriaRepository.buscarPorEstado(estado);
    }

    public Categoria actualizarCategoria(Long id, Categoria categoria) {
        Optional<Categoria> categoriaExistente = categoriaRepository.buscarPorId(id);

        if (categoriaExistente.isPresent()) {
            categoria.setIdCategoria(id);
            return categoriaRepository.guardar(categoria);
        }

        return null;
    }

    public Categoria actualizarParcialCategoria(Long id, Categoria datosActualizados) {
        Optional<Categoria> categoriaExistenteOpt = categoriaRepository.buscarPorId(id);

        if (categoriaExistenteOpt.isPresent()) {
            Categoria categoriaExistente = categoriaExistenteOpt.get();

            if (datosActualizados.getNombre() != null) {
                categoriaExistente.setNombre(datosActualizados.getNombre());
            }
            if (datosActualizados.getEstado() != null) {
                categoriaExistente.setEstado(datosActualizados.getEstado());
            }

            return categoriaRepository.guardar(categoriaExistente);
        }

        return null;
    }

    public boolean eliminarCategoria(Long id) {
        return categoriaRepository.eliminar(id);
    }

    public boolean existeCategoria(Long id) {
        return categoriaRepository.existeCategoria(id);
    }
}
