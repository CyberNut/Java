package SeaBattleV01.Controller;

import SeaBattleV01.Model.ModelInterface;
import SeaBattleV01.Model.Point;
import SeaBattleV01.View.SeaBattleView;

public class GameController implements Observer, IController {

    ModelInterface model;
    SeaBattleView view;

    public GameController(ModelInterface model) {
        this.model = model;
        model.registerObserver(this);
        view = new SeaBattleView(this, model);
        view.createView();
    }

    @Override
    public void startNewGame() {
        model.startNewGame();
    }

    @Override
    public void doShoot(int x, int y) {
        int fieldSize = model.getFieldSize();
        if(x>=0 && x<= fieldSize && y >= 0 && y<=fieldSize) {
            model.doShoot(x, y);
        }
    }

    @Override
    public void updateView() {
        int fieldSize = model.getFieldSize();
        Point[][] points = model.getField();
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                switch (points[i][j].getPointType()){
                    case ALIVE:
                        view.buttons[i][j].setText("X");
                        break;
                    case DEAD:
                        view.buttons[i][j].setText("V");
                        break;
                    case EMPTY:
                        view.buttons[i][j].setText(".");
                        break;
                    case BUSY:
                        view.buttons[i][j].setText("*");
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
