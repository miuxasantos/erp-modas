package com.erpmodas.repository.dependentes;

import com.erpmodas.model.entidades.dependentes.ItemCompra;
import com.erpmodas.repository.MovimentoEstoqueProjecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {
    List<ItemCompra> findByVariacaoProdutoId(Long variacaoProdutoId);
    List<ItemCompra> findByCompraId(Long id);

    @Query("""
        SELECT ic.variacaoProduto.id AS variacaoId, c.dataChegada AS data, ic.quantidade AS quantidade
        FROM ItemCompra ic
        JOIN ic.compra c
        WHERE ic.variacaoProduto.id IN :variacoes
        ORDER BY c.dataChegada, c.id
        """)
    List<MovimentoEstoqueProjecao> buscarEntradasDasVariacoes(@Param("variacoes") Collection<Long> variacoes);
}
