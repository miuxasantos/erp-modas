package com.erpmodas.dto.relatorios.compra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorMaisComprasDTO {
    private Long fornecedorId;
    private String fornecedor;
    private int quantidadeCompras;
    private int unidadesAdicionadas;
    private BigDecimal valorTotal;
}
