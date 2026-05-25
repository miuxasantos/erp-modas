package com.erpmodas.dto.dependentes.contasReceber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataRecebimentoDTO {
    private LocalDate dataRecebimento;
}
