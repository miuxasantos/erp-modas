package com.erpmodas.dto.relatorios.venda;

import com.erpmodas.enums.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaRelatorioItemDTO {
    private Long id;
    private LocalDate data;
    private String cliente;
    private FormaPagamento formaPagamento;
    private BigDecimal valorTotal;
}
