package com.erpmodas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Cargo {
    PROPRIETARIO("Proprietário"),
    VENDEDOR("Vendedor");

    @JsonValue
    private final String descricao;

    Cargo(String descricao) {
        this.descricao = descricao;
    }

}
