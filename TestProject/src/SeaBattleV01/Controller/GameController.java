package SeaBattleV01.Controller;

import SeaBattleV01.Model.ModelInterface;
import SeaBattleV01.Model.Point;
import SeaBattleV01.View.SeaBattleView;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameController implements Observer, IController {

    ModelInterface gamerField;
    ModelInterface compField;
    SeaBattleView view;

    public GameController(ModelInterface gamerField, ModelInterface compField) {
        this.gamerField = gamerField;
        this.compField = compField;
        view = new SeaBattleView(this);
        view.createView(gamerField.getFieldSize());
    }

    @Override
    public void startNewGame() {
        gamerField.registerObserver(this);
        compField.registerObserver(this);
        gamerField.startNewGame();
        compField.startNewGame();
    }

    @Override
    public void doShoot(int x, int y) {
        int fieldSize = compField.getFieldSize();
        int randomX = new Random().nextInt(fieldSize);
        int randomY = new Random().nextInt(fieldSize);
        ModelInterface.shootResult shootResult;

        if (x >= 0 && x <= fieldSize && y >= 0 && y <= fieldSize) {
            shootResult = compField.doShoot(x, y);
            view.addTextToGameLog("Gamer shoots on x:" + x + "  y:" + y + "  result:" + shootResult);
        }
        shootResult = gamerField.doShoot(randomX, randomY);
        view.addTextToGameLog("Comp shoots on x:" + x + "  y:" + y + "  result:" + shootResult);
        if (compField.isGameOver()) {
            view.addTextToGameLog("\nGamer is winner!!!");
            unsubscribeController();
        }
        if (gamerField.isGameOver()) {
            view.addTextToGameLog("\nComp is winner!!!");
            unsubscribeController();
        }
    }

    @Override
    public void updateView() {
        updateField(gamerField, view.gamerButtons);
        updateField(compField, view.compButtons);
    }

    private void unsubscribeController() {
        gamerField.removeObserver(this);
        compField.removeObserver(this);
    }

    private void updateField(ModelInterface field, final JButton[][] buttons) {
        int fieldSize = field.getFieldSize();
        Point[][] points = field.getField();
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                switch (points[i][j].getPointType()) {
                    case ALIVE:
                        if (field.isNeedToHideShips()) {
                            buttons[i][j].setText(".");
                            buttons[i][j].setBackground(Color.LIGHT_GRAY);
                        } else {
                            buttons[i][j].setText("X");
                            buttons[i][j].setBackground(Color.CYAN);
                        }
                        break;
                    case DEAD:
                        buttons[i][j].setText("V");
                        buttons[i][j].setBackground(Color.PINK);
                        break;
                    case EMPTY:
                        buttons[i][j].setText(".");
                        buttons[i][j].setBackground(Color.LIGHT_GRAY);
                        break;
                    case USED:
                        buttons[i][j].setText("-");
                        buttons[i][j].setBackground(Color.GRAY);
                        break;
                    case BUSY:
                        if (field.isNeedToHideShips()) {
                            buttons[i][j].setText(".");
                            buttons[i][j].setBackground(Color.LIGHT_GRAY);
                        } else {
                            buttons[i][j].setText("*");
                            buttons[i][j].setBackground(Color.ORANGE);
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void update() {
        updateView();
    }
}
