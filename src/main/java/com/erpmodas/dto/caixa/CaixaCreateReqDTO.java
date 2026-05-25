package com.erpmodas.dto.caixa;

import com.erpmodas.enums.StatusCaixa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaixaCreateReqDTO {
    private BigDecimal saldoAbertura;
    private LocalDate dataAbertura;
    private StatusCaixa statusCaixa;
}
