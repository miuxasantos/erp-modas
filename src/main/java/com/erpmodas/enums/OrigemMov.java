package com.erpmodas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum OrigemMov {
    COMPRA("compra"),
    VENDA("venda"),
    OUTRO("outro");

    @JsonValue
    private final String descricao;

    OrigemMov(String descricao) {
        this.descricao = descricao;
    }
}
