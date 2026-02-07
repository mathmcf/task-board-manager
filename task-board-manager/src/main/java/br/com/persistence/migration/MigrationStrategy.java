package br.com.persistence.migration;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.flywaydb.core.Flyway;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

@AllArgsConstructor
@Data
public class MigrationStrategy {

    private final Connection connection;

    public void executeMigration() {
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        try {
            try (FileOutputStream fos = new FileOutputStream("flyway.log")) {
                System.setOut(new PrintStream(fos));
                System.setErr(new PrintStream(fos));

                Flyway flyway = Flyway.configure().dataSource("jdbc:mysql://localhost:3306/board","root","12345")
                        .locations("classpath:db/migration").load();

                flyway.repair();
                flyway.migrate();

            }

        } catch (IOException ex) {
            ex.printStackTrace();

        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }
}
