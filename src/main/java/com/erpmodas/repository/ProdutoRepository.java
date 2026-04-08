package com.erpmodas.repository;

import com.erpmodas.model.entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByStatusTrue();
    List<Produto> findByCategoriaId(Long categoriaId);
    List<Produto> findByMarcaContainingIgnoreCase(String marca);
    List<Produto> findByDataInclusaoBetween(LocalDate inicio, LocalDate fim);
}
