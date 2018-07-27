package SeaBattleV01;

import SeaBattleV01.Model.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class WindowGameTest implements Observer {

    private JFrame jFrame = new JFrame("Battleships");
    private JPanel jPanel = new JPanel();
    private JButton[][] buttons;
    private GameField gameField;

    public static void main(String[] args) {

        WindowGameTest windowGameTest = new WindowGameTest();
        windowGameTest.initialiseWindow();
        windowGameTest.prepareGameField();
        windowGameTest.mainGameCircle();
    }

    private void initialiseWindow() {
        jFrame.setSize(700, 500);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel.setLayout(new GridLayout(10, 10));
        jFrame.add(jPanel);

        int fieldSize = GameField.SIZE;
        buttons = new JButton[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                JButton jButton = new JButton(".");
                buttons[i][j] = jButton;
                jPanel.add(jButton);
            }
        }
        jFrame.setVisible(true);
    }

    public void prepareGameField() {
        gameField = new GameField();
        gameField.registerObserver(this);
        gameField.placeRandomShips();

    }

    private void mainGameCircle() {
        Scanner scanner = new Scanner(System.in);
        int x;
        int y;
        while (!gameField.isGameOver()) {
            System.out.println("please enter coordinates:");
            x = scanner.nextInt();
            y = scanner.nextInt();
            gameField.doShoot(y - 1, x - 1);
//            if (gameField.doShoot(y - 1, x - 1) == GameField.shootResult.GOAL) {
//                System.out.println("GOAL");
//            } else {
//                System.out.println("YOU MISSED");
//            }
        }
    }

    @Override
    public void update(Point[][] points) {
        int fieldSize = GameField.SIZE;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                switch (points[i][j].getPointType()) {
                    case ALIVE:
                        buttons[i][j].setText("X");
                        break;
                    case BUSY:
                        buttons[i][j].setText("*");
                        break;
                    case DEAD:
                        buttons[i][j].setText("V");
                        break;
                }
            }
        }
        jFrame.repaint();
    }

}
