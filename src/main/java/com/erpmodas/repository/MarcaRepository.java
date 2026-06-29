package com.erpmodas.repository;

import com.erpmodas.model.entidades.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Optional<Marca> findByNome(String nome);
    List<Marca> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
}
