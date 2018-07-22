package SeaBattle;

import java.util.Scanner;

public class GameTest {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        GameField gameField = new GameField();
        createGamersShip(gameField);
        gameField.show();

        Shootable randomShooter = new RandomShooter();
        int x, y;

        x = in.nextInt();
        y = in.nextInt();
        Gamer gamer = new Gamer("Andrey", randomShooter);
        gamer.doShoot(gameField, x, y);
        System.out.println("Shoot on " + x + "  " + y);
        gameField.show();

    }

    public static void createGamersShip(GameField gameField)
    {
        RandomPlacement randomPlacement = new RandomPlacement();
        Ship boat1 = new Ship(1, "boat #1", randomPlacement);
        boat1.place(gameField);
        Ship boat2 = new Ship(1, "boat #2", randomPlacement);
        boat2.place(gameField);
        Ship boat3 = new Ship(1, "boat #3", randomPlacement);
        boat3.place(gameField);
        Ship boat4 = new Ship(1, "boat #4", randomPlacement);
        boat4.place(gameField);
        Ship destroyer1 = new Ship(2, "destroyer #1", randomPlacement);
        destroyer1.place(gameField);
        Ship destroyer2 = new Ship(2, "destroyer #2", randomPlacement);
        destroyer2.place(gameField);
        Ship destroyer3 = new Ship(2, "destroyer #3", randomPlacement);
        destroyer3.place(gameField);

        Ship frigate1 = new Ship(3, "frigate #1", randomPlacement);
        frigate1.place(gameField);
        Ship frigate2 = new Ship(3, "frigate #2", randomPlacement);
        frigate2.place(gameField);

        Ship battleship = new Ship(4, "battleShip #1", randomPlacement);
        battleship.place(gameField);


    }
}
