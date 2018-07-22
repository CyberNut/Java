package SeaBattle;

public class GameTest {
    public static void main(String[] args) {

        GameField gameField = new GameField();
        gameField.show();
        RandomPlacement randomPlacement = new RandomPlacement();
        Ship boat1 = new Ship(1, "boat #1", randomPlacement);
        boat1.place(gameField);
        Ship boat2 = new Ship(1, "boat #2", randomPlacement);
        boat2.place(gameField);
        Ship boat3 = new Ship(1, "boat #3", randomPlacement);
        boat3.place(gameField);
        Ship boat4 = new Ship(1, "boat #4", randomPlacement);
        boat4.place(gameField);
        Ship destroyer1 = new Ship(3, "destroyer #1", randomPlacement);
        destroyer1.place(gameField);
        Ship destroyer2 = new Ship(3, "destroyer #2", randomPlacement);
        destroyer2.place(gameField);

        Ship battleship = new Ship(4, "battleShip #1", randomPlacement);
        battleship.place(gameField);

        gameField.show();
    }
}
