package com.erpmodas.repository;

import java.time.LocalDate;

public interface MovimentoEstoqueProjecao {
    Long getVariacaoId();
    LocalDate getData();
    Integer getQuantidade();
}