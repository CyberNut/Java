package SeaBattleV01;

import SeaBattleV01.Controller.GameController;
import SeaBattleV01.Controller.IController;
import SeaBattleV01.Model.GameField;
import SeaBattleV01.Model.ModelInterface;

public class WindowGameTest {

    public static void main(String[] args) {

        ModelInterface gamerField = new GameField(false);
        ModelInterface compField = new GameField(true);
        IController controller = new GameController(gamerField, compField);
    }

}
