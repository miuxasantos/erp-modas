package com.erpmodas.dto.relatorios.venda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatoriosVendaDTO {
    private LocalDate inicio;
    private LocalDate fim;
    private Integer quantidadeVendas;
    private BigDecimal faturamento;
    private BigDecimal ticketMedio;
    private List<VendaPorDiaDTO> vendasPorDia;
    private List<VendaRelatorioItemDTO> vendas;
}
