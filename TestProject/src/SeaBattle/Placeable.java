package SeaBattle;

public interface Placeable {

    enum Direction {VERTICAL, HORIZONTAL}

    boolean place(Ship ship, GameField field);
}
