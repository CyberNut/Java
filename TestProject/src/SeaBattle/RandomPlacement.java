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
        Point currentPoint;
        boolean result = false;
        int shipSize = ship.getSize();

        if(shipSize == 0 )
            return false;

        randDirection = new Random().nextInt(2);
        if (randDirection == 0)
            direction = Direction.HORIZONTAL;
        else
            direction = Direction.VERTICAL;

        do {
            randomX = new Random().nextInt(GameField.SIZE);
            randomY = new Random().nextInt(GameField.SIZE);

            currentPoint = field.getPoint(randomX, randomY);
            //Проверим можно ли размещать в этой точке
            if (currentPoint.getPointType() == Point.type.EMPTY )
            {
                //Проверим поместится ли корабль если его размещать начиная с этой точки
                if(direction==Direction.HORIZONTAL) {
                    if (randomX + shipSize > GameField.SIZE)
                        continue;
                }
                else if(direction==Direction.VERTICAL) {
                    if (randomY + shipSize > GameField.SIZE)
                        continue;
                }

                //Помещается:
                int startX = randomX, startY=randomY;
                for (int i = 0; i < shipSize; i++) {
                    if(direction==Direction.HORIZONTAL) {
                        currentPoint = field.getPoint(startX + i, startY);
                    }
                    else if(direction==Direction.VERTICAL) {
                        currentPoint = field.getPoint(startX, startY + i);
                    }
                    currentPoint.setPointType(Point.type.ALIVE);
                    currentPoint.setShip(ship);
                    shipPoints.add(currentPoint);
                    result = true;
                }

            }
        } while(!result);




        return true;

    }
}
