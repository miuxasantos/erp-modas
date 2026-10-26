package com.erpmodas.repository.dependentes;

import com.erpmodas.model.entidades.dependentes.ItemVenda;
import com.erpmodas.repository.MovimentoEstoqueProjecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    List<ItemVenda> findByVariacaoProdutoId(Long variacaoProdutoId);
    List<ItemVenda> findByVendaId(Long id);

    @Query("""
        SELECT DISTINCT iv.variacaoProduto.id
        FROM ItemVenda iv
        WHERE iv.venda.dataVenda BETWEEN :inicio AND :fim
        """)
    List<Long> buscarVariacoesVendidasEntre(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );

    @Query("""
        SELECT iv.variacaoProduto.id AS variacaoId, v.dataVenda AS data, iv.quantidade AS quantidade
        FROM ItemVenda iv
        JOIN iv.venda v
        WHERE iv.variacaoProduto.id IN :variacoes
        ORDER BY v.dataVenda, v.id
        """)
    List<MovimentoEstoqueProjecao> buscarSaidasDasVariacoes(@Param("variacoes") Collection<Long> variacoes);
}
