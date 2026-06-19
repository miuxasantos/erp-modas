package com.erpmodas.dto.venda;

import com.erpmodas.dto.cliente.ClienteResponseDTO;
import com.erpmodas.dto.dependentes.contasReceber.ContasReceberDTO;
import com.erpmodas.dto.dependentes.itemVenda.ItemVendaReqDTO;
import com.erpmodas.dto.dependentes.itemVenda.ItemVendaResponseDTO;
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
public class VendaReqDTO {
    @NotNull
    private Long clienteId;
    @NotNull
    private LocalDate dataVenda;
    private String observacoes;
    @NotNull
    private FormaPagamento formaPagamento;
    @NotEmpty
    @NotNull
    private List<ItemVendaReqDTO> itensVenda;
    @NotNull
    private Integer numeroParcelas;
    @NotNull
    private BigDecimal valorTotal;
    private Double desconto;
}
