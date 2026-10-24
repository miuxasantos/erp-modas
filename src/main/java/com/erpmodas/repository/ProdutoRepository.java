package com.erpmodas.repository;

import com.erpmodas.model.entidades.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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
    List<Produto> findByMarca_NomeContainingIgnoreCase(String marca);
    List<Produto> findByDataInclusaoBetween(LocalDate inicio, LocalDate fim);

    Optional<Produto> findProdutoByNome(String nome);
    Optional<Produto> findByIdAndAtivoTrueAndNoCatalogoTrue(Long id);

    @Query("SELECT p.imagem FROM Produto p WHERE p.imagem IS NOT NULL AND p.imagem <> ''")
    List<String> findAllCaminhosImagem();

    @Query("""
        SELECT DISTINCT p FROM Produto p
        JOIN FETCH p.categoria
        LEFT JOIN FETCH p.variacoes variacao
        LEFT JOIN FETCH variacao.cor
        LEFT JOIN FETCH variacao.tamanho
        WHERE (:apenasAtivos = false OR p.ativo = true)
        AND (:noCatalogo IS NULL OR p.noCatalogo = :noCatalogo)
        AND (:categoriaId IS NULL OR p.categoria.id = :categoriaId)
        ORDER BY p.nome
        """)
    List<Produto> buscarParaRelatorioEstoque(@Param("apenasAtivos") boolean apenasAtivos,
                                             @Param("noCatalogo") Boolean noCatalogo,
                                             @Param("categoriaId") Long categoriaId
    );

    @EntityGraph(attributePaths = {"variacoes", "categoria", "marca"})
    @Query("SELECT p FROM Produto p WHERE p.ativo = true")
    Page<Produto> findByAtivoTrueAndPage(Pageable pageable);
    Page<Produto> findByCategoriaIdAndAtivoTrue(Long categoriaId, Pageable pageable);

    @Query("""
        SELECT DISTINCT p FROM Produto p
        LEFT JOIN p.variacoes v
        WHERE p.ativo = true
        AND (:q IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', CAST(:q AS string), '%')))
        AND (:categoriaId IS NULL OR p.categoria.id = :categoriaId)
        AND (:corId IS NULL OR v.cor.id = :corId)
        AND (:tamanhoId IS NULL OR v.tamanho.id = :tamanhoId)
        AND p.noCatalogo = true
    """)
    Page<Produto> buscarPublico(
            @Param("q") String q,
            @Param("categoriaId") Long categoriaId,
            @Param("corId") Long corId,
            @Param("tamanhoId") Long tamanhoId,
            Pageable pageable);
}
