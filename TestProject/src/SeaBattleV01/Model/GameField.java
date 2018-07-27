package SeaBattleV01.Model;

import SeaBattleV01.Controller.Observer;
import java.util.ArrayList;
import java.util.Random;

public class GameField implements ModelInterface {

    static final int SIZE = 10;
    private Point[][] field;
    private ArrayList<Ship> ships = new ArrayList<>();
    private ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void startNewGame() {
        field = new Point[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = new Point(i, j);
            }
        }
        placeRandomShips();
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public int getFieldSize() {
        return SIZE;
    }

    @Override
    public shootResult doShoot(int x, int y) {
        shootResult result;
        Point shootPoint = getPoint(x, y);
        if (shootPoint == null) {
            result = shootResult.INCORRECT;
        } else if (shootPoint.getPointType() == Point.type.ALIVE) {
            shootPoint.setPointType(Point.type.DEAD);
            result = shootResult.GOAL;
        } else {
            result = shootResult.MISSED;
        }
        notifyObservers();
        return result;
    }

    @Override
    public Point[][] getField() {
        return field;
    }

    public GameField() {
        field = new Point[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = new Point(i, j);
            }
        }
    }

    public Point getPoint(int x, int y) {
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
            return null;
        }
        return field[x][y];
    }

    public void setPoint(Point point) {
        field[point.getX()][point.getY()] = point;
    }

    public void displayOnConsole() {
        System.out.println("Game Field");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Point currentPoint = getPoint(i, j);
                if (currentPoint.getPointType() == Point.type.EMPTY) {
                    System.out.print(".");
                } else if (currentPoint.getPointType() == Point.type.ALIVE) {
                    System.out.print("X");
                } else if (currentPoint.getPointType() == Point.type.DEAD) {
                    System.out.print("-");
                } else if (currentPoint.getPointType() == Point.type.BUSY) {
                    System.out.print("*");
                }
            }
            System.out.println();
        }
    }

    @Override
    public boolean isGameOver() {
        for (Ship ship : ships) {
            for (Point point : ship.getPoints()) {
                if (point.getPointType() == Point.type.ALIVE) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeRandomShips() {
        Ship tempShip = new Ship(4, "BattleShip");
        tryToPlaceShip(tempShip);
        //Трехпалубные
        for (int i = 0; i < 2; i++) {
            tempShip = new Ship(3, "Frigate");
            tryToPlaceShip(tempShip);
        }
        //двухпалубные
        for (int i = 0; i < 3; i++) {
            tempShip = new Ship(2, "Destroyer");
            tryToPlaceShip(tempShip);
        }
        //однопалубные
        for (int i = 0; i < 4; i++) {
            tempShip = new Ship(1, "Boat");
            tryToPlaceShip(tempShip);
        }
        notifyObservers();
    }

    public boolean tryToPlaceShip(Ship ship) {
        Random random = new Random();
        boolean isHorizontal;
        boolean isPossiblePlace;
        int randomX;
        int randomY;
        do {
            isHorizontal = random.nextBoolean();
            randomX = isHorizontal ? random.nextInt(SIZE - ship.getSize() + 1) : random.nextInt(SIZE);
            randomY = !isHorizontal ? random.nextInt(SIZE - ship.getSize() + 1) : random.nextInt(SIZE);

            isPossiblePlace = checkIsPossiblePlaceShip(ship, randomX, randomY, isHorizontal);
            //System.out.println(" dir " + isHorizontal + " x " + randomX + " y " + randomY + " isPossible: " + isPossiblePlace);
        } while (!isPossiblePlace);

        if (isPossiblePlace) {
            placeShip(ship, randomX, randomY, isHorizontal);
        }
        return true;
    }

    public void placeShip(Ship ship, int x, int y, boolean isHorizontal) {
        Point tempPoint;
        for (int i = 0; i < ship.getSize(); i++) {
            tempPoint = isHorizontal ? getPoint(x + i, y) : getPoint(x, y + i);
            tempPoint.setPointType(Point.type.ALIVE);
            ship.addPoint(tempPoint);
        }
        markPointAroundShipAsBusy(ship);
        ships.add(ship);
    }

    private void markPointAroundShipAsBusy(Ship ship) {
        int x;
        int y;
        for (Point point : ship.getPoints()) {
            x = point.getX();
            y = point.getY();
            if (getPoint(x - 1, y - 1) != null && getPoint(x - 1, y - 1).isNotBusy()) {
                getPoint(x - 1, y - 1).setPointType(Point.type.BUSY);
            }
            if (getPoint(x, y - 1) != null && getPoint(x, y - 1).isNotBusy()) {
                getPoint(x, y - 1).setPointType(Point.type.BUSY);
            }
            if (getPoint(x + 1, y - 1) != null && getPoint(x + 1, y - 1).isNotBusy()) {
                getPoint(x + 1, y - 1).setPointType(Point.type.BUSY);
            }
            if (getPoint(x + 1, y) != null && getPoint(x + 1, y).isNotBusy()) {
                getPoint(x + 1, y).setPointType(Point.type.BUSY);
            }
            if (getPoint(x + 1, y + 1) != null && getPoint(x + 1, y + 1).isNotBusy()) {
                getPoint(x + 1, y + 1).setPointType(Point.type.BUSY);
            }
            if (getPoint(x, y + 1) != null && getPoint(x, y + 1).isNotBusy()) {
                getPoint(x, y + 1).setPointType(Point.type.BUSY);
            }
            if (getPoint(x - 1, y + 1) != null && getPoint(x - 1, y + 1).isNotBusy()) {
                getPoint(x - 1, y + 1).setPointType(Point.type.BUSY);
            }
            if (getPoint(x - 1, y) != null && getPoint(x - 1, y).isNotBusy()) {
                getPoint(x - 1, y).setPointType(Point.type.BUSY);
            }
        }

    }

    private boolean checkIsPossiblePlaceShip(Ship ship, int x, int y, boolean isHorizontal) {
        boolean isPossiblePlace = true;
        Point tempPoint;
        for (int i = 0; i < ship.getSize(); i++) {
            if (isHorizontal) {
                tempPoint = getPoint(x + i, y);
            } else {
                tempPoint = getPoint(x, y + i);
            }
            if (tempPoint != null && (tempPoint.getPointType() == Point.type.ALIVE || tempPoint.getPointType() == Point.type.BUSY)) {
                isPossiblePlace = false;
                break;
            }
        }
        return isPossiblePlace;
    }

}
