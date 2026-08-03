package com.erpmodas.repository;

import com.erpmodas.model.entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByAtivoTrue();
    List<Produto> findByCategoriaId(Long categoriaId);
    List<Produto> findByMarcaContainingIgnoreCase(String marca);
    List<Produto> findByDataInclusaoBetween(LocalDate inicio, LocalDate fim);

    Optional<Produto> findProdutoByNome(String nome);
    Optional<Produto> findProdutoById(Long id);

    @Query("SELECT p.imagem FROM Produto p WHERE p.imagem IS NOT NULL AND p.imagem <> ''")
    List<String> findAllCaminhosImagem();
}
