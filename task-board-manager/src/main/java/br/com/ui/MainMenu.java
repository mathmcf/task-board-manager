package br.com.ui;

import br.com.persistence.entity.BoardColumnEntity;
import br.com.persistence.entity.BoardColumnKindEnum;
import br.com.persistence.entity.BoardEntity;
import br.com.service.BoardQueryService;
import br.com.service.BoardService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.com.persistence.config.ConnectionConfig.getConnection;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException {
        System.out.println("Bem-vindo ao gerenciador de boards, escolha a opção desejada");
        var option = -1;
        while (true) {
            System.out.println(" 1 - Criar um novo board ");
            System.out.println(" 2 - Selecionar um board existente");
            System.out.println(" 3 - Excluir um board");
            System.out.println(" 4 - Sair");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida," +
                        " informa uma opção do menu");
            }
        }
    }

    private void createBoard() throws SQLException {

        var entity = new BoardEntity();

        System.out.println("Informe o nome do seu board");

        entity.setName(scanner.next());

        System.out.println("Seu board terá colunas além das 3 padrões? Se sim, informe a quantidade. Senão, digite 0\n");
        var additionalColumns = scanner.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board\n");
        var initialColumnName = scanner.next();
        var initialColumn = createColumn(initialColumnName, BoardColumnKindEnum.INITIAL, 0);
        columns.add(initialColumn);

        for (int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna de tarefa pendente do board\n");
            var pendingColumnName = scanner.next();
            var pendingColumn = createColumn(pendingColumnName, BoardColumnKindEnum.PENDING, i + 1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final do board");
        var finalColumnName = scanner.next();
        var finalColumn = createColumn(finalColumnName, BoardColumnKindEnum.PENDING, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento do board");
        var cancelColumnName = scanner.nextLine();
        var cancelColumn = createColumn(cancelColumnName, BoardColumnKindEnum.PENDING, additionalColumns + 1);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);

        try (var connection = getConnection()) {

            var service = new BoardService(connection);
            service.insert(entity);
        }
    }

    private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int order) {

        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);

        return boardColumn;
    }

    private void selectBoard() throws SQLException {

        System.out.println("Informe o id do board que deseja selecionar: ");
        var id = scanner.nextLong();

        try (var connection = getConnection()){

            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(id);

            if(optional.isPresent()){
                var boardMenu = new BoardMenu(optional.get());
                boardMenu.execute();
            } else {
                System.out.printf("Não foi encontrado um board com id %s\n", id);
            }

        }


    }

    private void deleteBoard() throws SQLException {

        System.out.println("Informe o id do board que será excluído: ");
        var id = scanner.nextLong();

        try (var connection = getConnection()) {

            var service = new BoardService(connection);

            if (service.delete(id)) {
                System.out.printf("O board %s foi excluído\n", id);

            } else {
                System.out.printf("Não foi encontrado um board com id %s\n", id);
            }
        }
    }
}
