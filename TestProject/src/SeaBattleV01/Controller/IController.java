package SeaBattleV01.Controller;

public interface IController {

    final int NETWORK_PORT = 7346;
    void startNewGame();
    void startNewNetworkGame();
    void connectToNetworkGame();
    void saveGame();
    void loadGame();
    void doShoot(int x, int y);
    void updateView();

}
