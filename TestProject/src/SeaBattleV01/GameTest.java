package SeaBattleV01;

import java.util.Scanner;

public class GameTest {

    static GameField gameField;

    public static void main(String[] args) {

        prepareGameField();
    }

    public static void prepareGameField() {
        Scanner scanner = new Scanner(System.in);
        int x;
        int y;
        gameField = new GameField();
        gameField.placeRandomShips();
        gameField.show();

        System.out.println("please enter coordinates:");
        x = scanner.nextInt();
        y = scanner.nextInt();

        if (gameField.doShoot(y-1, x-1) == GameField.shootResult.GOAL) {
            System.out.println("GOAL");
        } else {
            System.out.println("YOU MISSED");
        }
        gameField.show();

    }
}
