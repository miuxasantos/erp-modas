package com.erpmodas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum StatusCaixa {
    ABERTO("Aberto"),
    FECHADO("Fechado");

    @JsonValue
    private final String descricao;

    StatusCaixa(String descricao) {
        this.descricao = descricao;
    }
}
