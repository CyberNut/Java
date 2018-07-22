package SeaBattle;

import javax.swing.*;
import java.awt.*;

public class WindowGameTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sea battle");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JPanel panelGamer1 = new JPanel();
        frame.add(panelGamer1, BorderLayout.WEST);
        GridLayout gridLayout = new GridLayout(10, 10);
        panelGamer1.setLayout(gridLayout);
        JButton button1 = new JButton("Test");
        panelGamer1.add(button1);

        frame.setVisible(true);
    }
}
