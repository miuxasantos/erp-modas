package com.erpmodas.dto.venda;

import com.erpmodas.dto.cliente.ClienteResponseDTO;
import com.erpmodas.dto.dependentes.contasReceber.ContasReceberDTO;
import com.erpmodas.dto.dependentes.itemVenda.ItemVendaResponseDTO;
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
public class VendaResponseDTO {
    private Long id;
    private ClienteResponseDTO cliente;
    private LocalDate dataVenda;
    private String observacoes;
    private FormaPagamento formaPagamento;
    private List<ItemVendaResponseDTO> itensVenda;
    private List<ContasReceberDTO> contasReceber;
    private Integer numeroParcelas;
    private BigDecimal valorTotal;
    private Double desconto;
}
