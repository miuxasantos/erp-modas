package com.erpmodas.repository.apoio;

import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VariacaoProdutoRepository extends JpaRepository<VariacaoProduto, Long> {

    Optional<VariacaoProduto> findBySku(String sku);
    Optional<VariacaoProduto> findByProdutoIdAndCorIdAndTamanhoId(Long produtoId, Long corId, Long tamanhoId);

    List<VariacaoProduto> findByProdutoId(Long produtoId);
    List<VariacaoProduto> findByEstoqueLessThan(Integer quantidade);
    List<VariacaoProduto> findByCorId(Long corId);
    List<VariacaoProduto> findByTamanhoId(Long id);

    @Query("SELECT v.imagemEsp FROM VariacaoProduto v WHERE v.imagemEsp IS NOT NULL AND v.imagemEsp <> ''")
    List<String> findAllCaminhosImagem();
}
