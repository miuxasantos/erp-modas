package com.erpmodas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TipoAcaoAud {
    CREATE,
    UPDATE,
    DELETE,
    LOGIN,
    LOGIN_FALHA
}
