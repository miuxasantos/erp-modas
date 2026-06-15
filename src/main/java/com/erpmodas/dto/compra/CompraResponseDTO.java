package com.erpmodas.dto.compra;

import com.erpmodas.dto.dependentes.contasPagar.ContasPagarDTO;
import com.erpmodas.dto.dependentes.itemCompra.ItemCompraResponseDTO;
import com.erpmodas.dto.fornecedor.FornecedorResponseDTO;
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
public class CompraResponseDTO {
    private Long id;
    private FornecedorResponseDTO fornecedor;
    private String lote;
    private LocalDate dataChegada;
    private String observacoes;
    private FormaPagamento formaPagamento;
    private Integer numeroParcelas;
    private BigDecimal valorTotal;
    private List<ItemCompraResponseDTO> itensCompra;
    private List<ContasPagarDTO> contasPagarDTO;
}
