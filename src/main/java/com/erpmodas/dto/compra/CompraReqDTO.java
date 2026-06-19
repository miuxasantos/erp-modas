package com.erpmodas.dto.compra;

import com.erpmodas.dto.dependentes.contasPagar.ContasPagarDTO;
import com.erpmodas.dto.dependentes.itemCompra.ItemCompraReqDTO;
import com.erpmodas.dto.dependentes.itemCompra.ItemCompraResponseDTO;
import com.erpmodas.enums.FormaPagamento;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraReqDTO {
    @NotNull
    private Long fornecedorId;
    @NotNull
    private String lote;
    private LocalDate dataChegada;
    @NotNull
    private String observacoes;
    @NotNull
    private FormaPagamento formaPagamento;
    private Integer numeroParcelas;
    @NotNull
    private BigDecimal valorTotal;
    @NotEmpty
    @NotNull
    private List<ItemCompraReqDTO> itensCompra;
}
