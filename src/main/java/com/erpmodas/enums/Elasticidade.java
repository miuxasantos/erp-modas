package com.erpmodas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Elasticidade {
    BAIXA_ELASTICIDADE("Baixa Elasticidade"),
    MEDIA_ELASTICIDADE("Média Elasticidade"),
    ALTA_ELASTICIDADE("Alta Elasticidade");

    @JsonValue
    private final String descricao;

    Elasticidade(String descricao) {
        this.descricao = descricao;
    }
}
