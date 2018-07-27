package SeaBattleV01.View;

import SeaBattleV01.Controller.IController;

import javax.swing.*;

public class SeaBattleView {

    IController controller;
    JFrame viewFrame;
    JPanel viewPanel;
    JMenuBar jMenuBar;
    JMenu jMenu;
    JMenuItem startNewGameMenuItem;
    JMenuItem exitMenuItem;
    JButton[][] buttons;

    public SeaBattleView(IController controller) {
        this.controller = controller;
    }

    public void createView() {

    }
}
