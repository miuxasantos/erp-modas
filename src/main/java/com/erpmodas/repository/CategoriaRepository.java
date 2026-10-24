package com.erpmodas.repository;

import com.erpmodas.model.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNomeContainingIgnoreCase(String nome);

    @Query("""
         SELECT DISTINCT c FROM Produto p
         JOIN p.categoria c
         WHERE p.ativo = true
         AND p.noCatalogo = true
         ORDER BY c.nome
    """)
    List<Categoria> findComProdutosAtivos();
}
