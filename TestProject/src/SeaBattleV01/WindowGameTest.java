package SeaBattleV01;

import javax.swing.*;
import java.awt.*;

public class WindowGameTest {

    private static JFrame jFrame = new JFrame("Battleships");
    private static JPanel jPanel = new JPanel();

    public static void main(String[] args) {
        initialiseWindow();
    }

    private static void initialiseWindow() {
        jFrame.setSize(700, 500);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel.setLayout(new GridLayout(10, 10));
        jFrame.add(jPanel);
        jFrame.setVisible(true);
    }
}
