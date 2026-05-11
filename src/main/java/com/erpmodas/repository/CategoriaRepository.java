package com.erpmodas.repository;

import com.erpmodas.model.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNomeContainingIgnoreCase(String nome);
}
