package br.com.persistence.config;

import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor
public final class ConnectionConfig {

    public static Connection getConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/board";
        String user = "root";
        String password = "12345";
        Connection connection = DriverManager.getConnection(url,user,password);
        connection.setAutoCommit(false);

        return connection;

    }

}
