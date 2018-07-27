package SeaBattleV01.Model;

import SeaBattleV01.Observer;

public interface ModelInterface {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
