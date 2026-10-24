package com.erpmodas.dto.relatorios.financeiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioFiscalMensalDTO {
    private int ano;
    private int mes;
    private List<DiaFiscalDTO> dias;
    private List<DespesaFiscalDTO> despesas;
    private DiaFiscalDTO totaisMes;
}