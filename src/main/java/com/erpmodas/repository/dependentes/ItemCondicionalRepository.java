package com.erpmodas.repository.dependentes;

import com.erpmodas.model.entidades.dependentes.ItemCondicional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCondicionalRepository extends JpaRepository<ItemCondicional, Long> {
    List<ItemCondicional> findByVariacaoProdutoId(Long variacaoProdutoId);
}
