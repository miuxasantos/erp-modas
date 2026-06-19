package com.erpmodas.dto.caixa;

import com.erpmodas.enums.StatusCaixa;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaixaCreateReqDTO {
    @NotNull
    private BigDecimal saldoAbertura;
    @NotNull
    private LocalDate dataAbertura;
    private StatusCaixa statusCaixa;
}
