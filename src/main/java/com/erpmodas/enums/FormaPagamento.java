package com.erpmodas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum FormaPagamento {
    PIX("Pix"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    DINHEIRO("Dinheiro"),
    BOLETO("Boleto");

    @JsonValue
    private final String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }
}
