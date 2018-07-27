package SeaBattleV01.Model;

import SeaBattleV01.Controller.Observer;

public interface ModelInterface {
    enum shootResult {MISSED, INCORRECT, GOAL}
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
    boolean isGameOver();
    int getFieldSize();
    Point[][] getField();
    void startNewGame();
    shootResult doShoot(int x, int y);
    boolean isNeedToHideShips();

}
