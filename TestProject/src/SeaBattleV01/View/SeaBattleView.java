package SeaBattleV01.View;

import SeaBattleV01.Controller.IController;
import SeaBattleV01.Model.ModelInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeaBattleView {

    IController controller;
    ModelInterface model;
    JFrame viewFrame;
    JPanel viewGamerPanel;
    JPanel viewCompPanel;
    JPanel viewCenterPanel;
    JTextArea gameLog;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem startNewGameMenuItem;
    JMenuItem exitMenuItem;
    StatusBar statusBar;
    public JButton[][] gamerButtons;
    public JButton[][] compButtons;

    public SeaBattleView(IController controller, ModelInterface model) {
        this.controller = controller;
        this.model = model;
        //model.registerObserver(this);
    }

    public void createView() {
        final int fieldSize = model.getFieldSize();
        viewCenterPanel = new JPanel();
        gameLog = new JTextArea();
        viewCenterPanel.add(gameLog);
        viewGamerPanel = new JPanel(new GridLayout(fieldSize, fieldSize));
        viewCompPanel = new JPanel(new GridLayout(fieldSize, fieldSize));
        gamerButtons = new JButton[fieldSize][fieldSize];
        compButtons = new JButton[fieldSize][fieldSize];
        fillPanel(viewGamerPanel, gamerButtons, fieldSize);
        fillPanel(viewCompPanel, compButtons, fieldSize);

        viewFrame = new JFrame("Sea battle v 0.1");
        viewFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        viewFrame.setSize(1000, 600);
        viewFrame.add(viewGamerPanel, BorderLayout.WEST);
        viewFrame.add(viewCompPanel, BorderLayout.EAST);
        viewFrame.add(viewCenterPanel, BorderLayout.CENTER);
        statusBar = new StatusBar();
        viewFrame.add(statusBar,BorderLayout.SOUTH);
        createMenu();
        viewFrame.setVisible(true);
    }

    private void fillPanel(JPanel jPanel, final JButton[][] jButtons, final int fieldSize) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                JButton tempButton = new JButton(".");
                tempButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int k = 0; k < fieldSize; k++) {
                            for (int l = 0; l < fieldSize; l++) {
                                if (jButtons[k][l] == e.getSource()) {
                                    controller.doShoot(k, l);
                                }
                            }
                        }
                    }
                });
                jButtons[i][j] = tempButton;
                jPanel.add(tempButton);
            }
        }
    }

    private void createMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        startNewGameMenuItem = new JMenuItem("New game");
        menu.add(startNewGameMenuItem);
        startNewGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearGameLog();
                controller.startNewGame();
                addTextToGameLog("Starting game");
            }
        });
        exitMenuItem = new JMenuItem("Exit");
        menu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuBar.add(menu);
        viewFrame.setJMenuBar(menuBar);
    }

    public void addTextToGameLog(String text) {
        gameLog.append("\n" + text);
    }

    public void clearGameLog() {
        gameLog.setText("");
    }

    public class StatusBar extends JLabel {

        public StatusBar() {
            super();
            super.setPreferredSize(new Dimension(100, 16));
            setMessage("Ready");
        }

        public void setMessage(String message) {
            setText(" " + message);
        }
    }
}
