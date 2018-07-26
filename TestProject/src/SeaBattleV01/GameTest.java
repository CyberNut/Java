package SeaBattleV01;

import java.util.Scanner;

public class GameTest {

    static GameField gameField;

    public static void main(String[] args) {

        prepareGameField();
        mainGameCircle();
    }

    public static void prepareGameField() {
        gameField = new GameField();
        gameField.placeRandomShips();
        gameField.displayOnConsole();
    }

    private static void mainGameCircle() {
        Scanner scanner = new Scanner(System.in);
        int x;
        int y;
        while (!gameField.isGameOver()) {
            System.out.println("please enter coordinates:");
            x = scanner.nextInt();
            y = scanner.nextInt();

            if (gameField.doShoot(y - 1, x - 1) == GameField.shootResult.GOAL) {
                System.out.println("GOAL");
            } else {
                System.out.println("YOU MISSED");
            }
            gameField.displayOnConsole();
        }
    }
}
