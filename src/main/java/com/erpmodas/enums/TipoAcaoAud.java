package com.erpmodas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TipoAcaoAud {
    CREATE("Create"),
    UPDATE("Update"),
    DELETE("Delete"),
    LOGIN("Login"),
    LOGIN_FALHA("Falha login");


    @JsonValue
    private final String descricao;

    TipoAcaoAud(String descricao) {
        this.descricao = descricao;
    }
}
