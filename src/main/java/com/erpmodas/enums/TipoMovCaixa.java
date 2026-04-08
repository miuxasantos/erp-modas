package com.erpmodas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TipoMovCaixa {
    ENTRADA("Entrada"),
    SAIDA("Saída");

    @JsonValue
    private final String descricao;

    TipoMovCaixa(String descricao) {
        this.descricao = descricao;
    }
}
