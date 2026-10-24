package com.erpmodas.dto.relatorios.venda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaAgrupadaDTO {
    private String chave;
    private String nome;
    private int quantidade;
    private BigDecimal faturamento;
}
