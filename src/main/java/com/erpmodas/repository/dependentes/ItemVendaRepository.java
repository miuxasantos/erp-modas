package com.erpmodas.repository.dependentes;

import com.erpmodas.model.entidades.dependentes.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    List<ItemVenda> findByVariacaoProdutoId(Long variacaoProdutoId);
}
