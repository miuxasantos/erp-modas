package com.erpmodas.helpers.security;

public final class RoleAuthority {
    public static final String PROPRIETARIO = "hasRole('ADMIN')";
    public static final String VENDEDOR =  "hasRole('VENDEDOR)";
    public static final String VENDEDOR_OU_PROPRIETARIO = "hasAnyRole('VENDEDOR', 'PROPRIETARIO'";

    private RoleAuthority() {}
}
