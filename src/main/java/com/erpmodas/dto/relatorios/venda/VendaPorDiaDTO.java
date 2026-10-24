package com.erpmodas.dto.relatorios.venda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaPorDiaDTO {
    private LocalDate data;
    private Integer quantidade;
    private BigDecimal total;
}
