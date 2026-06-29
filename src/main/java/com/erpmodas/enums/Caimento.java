package com.erpmodas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Caimento {
    FLUIDO("Fluído"),
    ESTRUTURADO("Estruturado"),
    INTERMEDIARIO("Intermediário");

    @JsonValue
    private final String descricao;

    Caimento(String descricao) {
        this.descricao = descricao;
    }
}
