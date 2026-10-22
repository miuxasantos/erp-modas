package com.erpmodas.repository.apoio;

import com.erpmodas.model.entidades.apoio.Cor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CorRepository extends JpaRepository<Cor, Long> {

    Optional<Cor> findByNome(String nome);
    List<Cor> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

    @Query("""
         SELECT DISTINCT c FROM VariacaoProduto v
         JOIN v.cor c
         WHERE v.produto.ativo = true
         ORDER BY c.nome
    """)
    List<Cor> findComVariacoesAtivas();
}
