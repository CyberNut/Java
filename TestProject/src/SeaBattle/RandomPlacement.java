package SeaBattle;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlacement implements Placeable {

    @Override
    public boolean place(Ship ship, GameField field) {

        ArrayList<Point> shipPoints = ship.getPoints();
        Direction direction;
        int randomX, randomY;
        int randDirection;
        Point startPoint;
        boolean result = false;
        int shipSize = ship.getSize();

        if(shipPoints.size() == 0 )
            return false;

        randDirection = new Random().nextInt(2);
        if (randDirection == 0)
            direction = Direction.HORIZONTAL;
        else
            direction = Direction.VERTICAL;

        do {
            randomX = new Random().nextInt(GameField.SIZE);
            randomY = new Random().nextInt(GameField.SIZE);

            startPoint = field.getPoint(randomX, randomY);
            //Проверим можно ли размещать в этой точке
            if (startPoint.getPointType() == Point.type.EMPTY )
            {
                //Проверим поместится ли корабль если его размещать начиная с этой точки
                if(direction==Direction.HORIZONTAL) {
                    if (randomX + shipSize > GameField.SIZE)
                        continue;
                }
                else if(direction==Direction.HORIZONTAL) {
                    if (randomY + shipSize > GameField.SIZE)
                        continue;
                }
                for (Point point:shipPoints {
                    field[]
                }
                for (int i = 0; i < shipSize; i++) {
                    field.setPoint(sh);
                }

            }
        } while(!result);




        return true;

    }
}
