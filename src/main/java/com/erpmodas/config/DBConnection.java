package com.erpmodas.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;


public class DBConnection {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("DB_URL");
        String usuario = dotenv.get("DB_USER");
        String senha = dotenv.get("DB_PASS");

        try (Connection conn = DriverManager.getConnection(url, usuario, senha)) {
            System.out.println("Conectado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro na conexão:");
            e.printStackTrace();
        }
    }
}
