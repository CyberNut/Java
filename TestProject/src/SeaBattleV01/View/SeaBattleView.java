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
    JPanel viewPanel;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem startNewGameMenuItem;
    JMenuItem exitMenuItem;
    public JButton[][] buttons;

    public SeaBattleView(IController controller, ModelInterface model) {
        this.controller = controller;
        this.model = model;
        //model.registerObserver(this);
    }

    public void createView() {
        final int fieldSize = model.getFieldSize();
        viewPanel = new JPanel(new GridLayout(fieldSize, fieldSize));
        buttons = new JButton[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                JButton tempButton = new JButton(".");
                tempButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int k = 0; k < fieldSize; k++) {
                            for (int l = 0; l < fieldSize; l++) {
                                if (buttons[k][l] == e.getSource()) {
                                    controller.doShoot(k, l);
                                }
                            }
                        }
                    }
                });
                buttons[i][j] = tempButton;
                viewPanel.add(tempButton);
            }
        }
        viewFrame = new JFrame("Sea battle v 0.1");
        viewFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        viewFrame.setSize(700, 500);
        viewFrame.add(viewPanel);

        createMenu();
        viewFrame.setVisible(true);
    }

    private void createMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        startNewGameMenuItem = new JMenuItem("New game");
        menu.add(startNewGameMenuItem);
        startNewGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startNewGame();
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
}
