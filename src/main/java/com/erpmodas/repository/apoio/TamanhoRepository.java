package com.erpmodas.repository.apoio;

import com.erpmodas.enums.TamanhoEnum;
import com.erpmodas.model.entidades.apoio.Tamanho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TamanhoRepository extends JpaRepository<Tamanho, Long> {

    Optional<Tamanho> findByTamanho(TamanhoEnum tamanho);
    boolean existsByTamanho(TamanhoEnum tamanhoEnum);

    List<Tamanho> findAllByOrderByTamanhoAsc();

    @Query("""
        SELECT t FROM VariacaoProduto v
        JOIN v.tamanho t
        WHERE v.produto.ativo = true
        AND v.produto.noCatalogo = true
    """)
    List<Tamanho> findComVaricoesAtivas();
}
