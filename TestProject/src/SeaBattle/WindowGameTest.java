package SeaBattle;

import javax.swing.*;
import java.awt.*;

public class WindowGameTest extends JFrame {

    private JPanel panelGamer1;
    GameField gamerField;

    public static void main(String[] args) {
        WindowGameTest windowGameTest = new WindowGameTest();
        windowGameTest.windowInit();
        GameField gamerField = new GameField();
        windowGameTest.createGamersShip(gamerField);
        windowGameTest.showField(gamerField);
    }

    private void windowInit() {
        setTitle("Sea battle");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);

        panelGamer1 = new JPanel();
        add(panelGamer1, BorderLayout.WEST);
        GridLayout gridLayout = new GridLayout(10, 10);
        panelGamer1.setLayout(gridLayout);
        JButton button1 = new JButton("Test");
        panelGamer1.add(button1);

        setVisible(true);
    }

    public void createGamersShip(GameField gameField) {
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

    private void showField(GameField field) {
        Point[][] points = field.getField();
        for (int i = 0; i < GameField.SIZE; i++) {
            for (int j = 0; j < GameField.SIZE; j++) {

            }

        }
    }
}
