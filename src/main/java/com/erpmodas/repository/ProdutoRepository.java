package com.erpmodas.repository;

import com.erpmodas.model.entidades.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    Optional<Produto> findByIdAndAtivoTrue(Long id);


    @Query("SELECT p.imagem FROM Produto p WHERE p.imagem IS NOT NULL AND p.imagem <> ''")
    List<String> findAllCaminhosImagem();

    @Query("SELECT p FROM Produto p WHERE p.ativo = true")
    Page<Produto> findByAtivoTrueAndPage(Pageable pageable);
    Page<Produto> findByCategoriaIdAndAtivoTrue(Long categoriaId, Pageable pageable);

    @Query("""
        SELECT DISTINCT p FROM Produto p
        LEFT JOIN p.variacoes v
        WHERE p.ativo = true
        AND (:q IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :q, '%'))
             OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :q, '%')))
        AND (:categoriaId IS NULL OR p.categoria.id = :categoriaId)
        AND (:corId IS NULL OR v.cor.id = :corId)
        AND (:tamanhoId IS NULL OR v.tamanho.id = :tamanhoId)
    """)
    Page<Produto> buscarPublico(
            @Param("q") String q,
            @Param("categoriaId") Long categoriaId,
            @Param("corId") Long corId,
            @Param("tamanhoId") Long tamanhoId,
            Pageable pageable);
}
