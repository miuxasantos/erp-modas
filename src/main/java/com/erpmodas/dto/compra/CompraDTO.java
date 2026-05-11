package com.erpmodas.dto.compra;

import com.erpmodas.dto.dependentes.contasPagar.ContasPagarDTO;
import com.erpmodas.dto.dependentes.itemCompra.ItemCompraDTO;
import com.erpmodas.dto.fornecedor.FornecedorDTO;
import com.erpmodas.enums.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraDTO {

    private Long id;
    private FornecedorDTO fornecedor;
    private String lote;
    private LocalDate dataChegada;
    private String observacoes;
    private FormaPagamento formaPagamento;
    private Integer numeroParcelas;
    private BigDecimal valorTotal;
    private List<ItemCompraDTO> itensCompra;
    private List<ContasPagarDTO> contasPagarDTO;
}
