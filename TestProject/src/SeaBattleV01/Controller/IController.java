package SeaBattleV01.Controller;

public interface IController {

    void startNewGame();
    void startNewNetworkGame();
    void saveGame();
    void loadGame();
    void doShoot(int x, int y);
    void updateView();

}
