package br.com;

import br.com.persistence.migration.MigrationStrategy;
import br.com.ui.MainMenu;

import java.sql.SQLException;

import static br.com.persistence.config.ConnectionConfig.getConnection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {

        try(var connection = getConnection()){
            new MigrationStrategy(connection).executeMigration();
            
            System.out.println("Migração executada com sucesso");
        }

        new MainMenu().execute();

    }
}