package br.com.ui;

import br.com.persistence.entity.BoardEntity;
import java.util.Scanner;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardMenu {

    private final BoardEntity entity;
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");


    public void execute() {

        System.out.printf("Bem-vindo ao board %s, selecione a operação desejada", entity.getId());

        var option = -1;
        while (true) {
            System.out.println(" 1 - Criar um card ");
            System.out.println(" 2 - Mover um card");
            System.out.println(" 3 - Bloquear um card");
            System.out.println(" 4 - Desbloquear um card");
            System.out.println(" 5 - Cancelar um card");
            System.out.println(" 6 - Voltar para o menu anterior");
            System.out.println(" 7 - Visualizar colunas com cards");
            System.out.println(" 8 - Visualizar cards");
            System.out.println(" 9 - Sair");


            System.out.println(" 4 - Sair");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> createCard();
                case 2 -> moveCardToNextColumn();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCard();
                case 6 -> showColumns();
                case 7 -> showColumn();
                case 8 -> showCard();
                case 9 -> System.out.println("Voltando para o menu anterior...");
                case 10 -> System.exit(0);
                default -> System.out.println("Opção inválida," +
                        " informa uma opção do menu");
            }

    }
}

    private void createCard() {
    }

    private void showColumns() {
    }

    private void showColumn() {
    }

    private void showCard() {
    }

    private void cancelCard() {
    }

    private void unblockCard() {
    }

    private void blockCard() {
    }

    private void moveCardToNextColumn(){
    }


}



