package com.erpmodas.dto.dependentes.movimentacoesCaixa;

import com.erpmodas.enums.OrigemMov;
import com.erpmodas.enums.TipoMovCaixa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacoesCaixaDTO {

    private Long id;
    private LocalDate data;
    private TipoMovCaixa tipoMovCaixa;
    private BigDecimal valor;
    private String descricao;
    private OrigemMov origemMov;
    private Long origemId;
}
