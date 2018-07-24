package SeaBattleV01;

public class GameTest {

    static GameField gameField;

    public static void main(String[] args) {

        prepareGameField();
    }

    public static void prepareGameField() {
        gameField = new GameField();
        gameField.placeRandomShips();
    }
}
