package com.erpmodas.helpers;

import org.apache.commons.lang3.StringEscapeUtils;

public class Helpers {


    public static String normalizarNome(String nome) {
            return nome.trim().toUpperCase();
    }

    public static void validar(String hex) {
        if (!hex.matches("^#([A-Fa-f0-9]{6})$")) {
            throw new RuntimeException("HEX inválido");
        }
    }


    public static String removerTags(String input) {
        return input.replaceAll("<[^>]*>", "");
    }


    public static String escaparHtml(String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }
}
