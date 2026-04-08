package com.erpmodas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum StatusConta {
    PENDENTE("Pendente"),
    PAGO("Pago"),
    VENCIDO("Vencido"),
    CANCELADO("Cancelado");

    @JsonValue
    private final String descricao;

    StatusConta(String descricao) {
        this.descricao = descricao;
    }
}
