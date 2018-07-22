package SeaBattle;

import java.util.Random;

public class RandomShooter implements Shootable {

    @Override
    public boolean doShoot(GameField field, int x, int y) {

        int fieldSize = GameField.SIZE;

        if (x >= 0 && x < fieldSize && y >= 0 && y < fieldSize) {
            Point currentPoint = field.getPoint(x, y);
            if (currentPoint.getPointType() == Point.type.ALIVE) {
                currentPoint.setPointType(Point.type.DEAD);

                return true;
            }
            return false;

        } else {
            return false;
        }
    }

}
