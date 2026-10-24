package com.erpmodas.dto.dependentes.contasPagar;

import com.erpmodas.enums.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataPagamentoDTO {
    private LocalDate dataPagamento;
    private FormaPagamento formaPagamento;
}
