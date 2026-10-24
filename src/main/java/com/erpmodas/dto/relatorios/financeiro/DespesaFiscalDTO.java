package com.erpmodas.dto.relatorios.financeiro;

import com.erpmodas.enums.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaFiscalDTO {
    private LocalDate data;
    private String descricao;
    private FormaPagamento formaPagamento;
    private BigDecimal valor;
}