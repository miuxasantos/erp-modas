package com.erpmodas.dto.relatorios.estoque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TempoSaidaDTO {
    private int pecasVendidas;
    private BigDecimal mediaDias;
}