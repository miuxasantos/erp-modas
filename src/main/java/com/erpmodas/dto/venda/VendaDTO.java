package com.erpmodas.dto.venda;

import com.erpmodas.dto.cliente.ClienteDTO;
import com.erpmodas.dto.dependentes.contasReceber.ContasReceberDTO;
import com.erpmodas.dto.dependentes.itemVenda.ItemVendaDTO;
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
public class VendaDTO {
    private Long id;
    private ClienteDTO cliente;
    private Long clienteId;
    private LocalDate dataVenda;
    private String observacoes;
    private FormaPagamento formaPagamento;
    private List<ItemVendaDTO> itensVenda;
    private List<ContasReceberDTO> contasReceber;
    private Integer numeroParcelas;
    private BigDecimal valorTotal;
    private Double desconto;
}
