package com.erpmodas.repository.dependentes;

import com.erpmodas.model.entidades.dependentes.ItemCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {
    List<ItemCompra> findByVariacaoProdutoId(Long variacaoProdutoId);
}
