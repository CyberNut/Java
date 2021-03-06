package SeaBattleV01.View;

import SeaBattleV01.Controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeaBattleView {

    IController controller;
    JFrame viewFrame;
    JPanel viewGamerPanel;
    JPanel viewCompPanel;
    JPanel viewCenterPanel;
    JTextArea gameLog;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem startNewGameMenuItem;
    JMenuItem startNewNetworkGameMenuItem;
    JMenuItem connectToNetworkGameMenuItem;
    JMenuItem saveGameMenuItem;
    JMenuItem loadGameMenuItem;
    JMenuItem exitMenuItem;
    StatusBar statusBar;
    public JButton[][] gamerButtons;
    public JButton[][] enemyButtons;

    public SeaBattleView(IController controller) {
        this.controller = controller;
    }

    public void createView(int fieldSize) {
        viewCenterPanel = new JPanel();
        gameLog = new JTextArea(35,35);
        viewCenterPanel.add(new JScrollPane(gameLog));
        viewGamerPanel = new JPanel(new GridLayout(fieldSize, fieldSize));
        viewCompPanel = new JPanel(new GridLayout(fieldSize, fieldSize));
        gamerButtons = new JButton[fieldSize][fieldSize];
        enemyButtons = new JButton[fieldSize][fieldSize];
        fillPanel(viewGamerPanel, gamerButtons, fieldSize);
        fillPanel(viewCompPanel, enemyButtons, fieldSize);

        viewFrame = new JFrame("Sea battle v 0.1");
        viewFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        viewFrame.setSize(1300, 700);
        viewFrame.add(viewGamerPanel, BorderLayout.WEST);
        viewFrame.add(viewCompPanel, BorderLayout.EAST);
        viewFrame.add(viewCenterPanel, BorderLayout.CENTER);
        statusBar = new StatusBar();
        viewFrame.add(statusBar, BorderLayout.SOUTH);
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
                                if (jButtons[k][l].equals(e.getSource())) {
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
        startNewNetworkGameMenuItem = new JMenuItem("New network game");
        menu.add(startNewNetworkGameMenuItem);
        startNewNetworkGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearGameLog();
                controller.startNewNetworkGame();
                addTextToGameLog("Starting network game...");
            }
        });
        connectToNetworkGameMenuItem = new JMenuItem("Connect to network game");
        menu.add(connectToNetworkGameMenuItem);
        connectToNetworkGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearGameLog();
                addTextToGameLog("Connecting to network game...");
                controller.connectToNetworkGame();
            }
        });
        menu.addSeparator();
        saveGameMenuItem = new JMenuItem("Save game");
        menu.add(saveGameMenuItem);
        saveGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveGame();
            }
        });
        loadGameMenuItem = new JMenuItem("Load game");
        menu.add(loadGameMenuItem);
        loadGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadGame();
            }
        });
        menu.addSeparator();
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

    public void showMessageBox(String text) {
        JOptionPane.showMessageDialog(null, text);
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
