package SeaBattleV01;

import java.util.ArrayList;
import java.util.Random;

public class GameField {

    static final int SIZE = 10;
    private Point[][] field;
    private ArrayList<Ship> ships = new ArrayList<>();

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

    public Point[][] getField() {
        return field;
    }

    public void show() {
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

    public void placeRandomShips() {
        Ship tempShip = new Ship(4, "BattleShip");
        boolean isSuccessPlaced = false;
        do {
            isSuccessPlaced = tryToPlaceShip(tempShip);
        } while (!isSuccessPlaced);
        //Трехпалубные
        for (int i = 0; i < 2 ; i++) {
            tempShip = new Ship(3, "Frigate");
            isSuccessPlaced = false;
            do {
                isSuccessPlaced = tryToPlaceShip(tempShip);
            } while (!isSuccessPlaced);
        }
        //двухпалубные
        for (int i = 0; i < 3 ; i++) {
            tempShip = new Ship(2, "Destroyer");
            isSuccessPlaced = false;
            do {
                isSuccessPlaced = tryToPlaceShip(tempShip);
            } while (!isSuccessPlaced);
        }
        show();
        //однопалубные
        for (int i = 0; i < 4 ; i++) {
            tempShip = new Ship(1, "Boat");
            isSuccessPlaced = false;
            do {
                isSuccessPlaced = tryToPlaceShip(tempShip);
            } while (!isSuccessPlaced);
        }

    }

    public boolean tryToPlaceShip(Ship ship) {
        Random random = new Random();
        boolean isHorizontal;
        boolean isPossiblePlace;
        int x;
        int y;

        do {
            isHorizontal = random.nextBoolean();
            x = isHorizontal ? random.nextInt(SIZE - ship.getSize() + 1) : random.nextInt(SIZE);
            y = !isHorizontal ? random.nextInt(SIZE - ship.getSize() + 1) : random.nextInt(SIZE);
            //System.out.println(" dir " + isHorizontal + " x " + x + " y " + y);
            isPossiblePlace = checkIsPossiblePlaceShip(ship, x, y, isHorizontal);
        } while (!isPossiblePlace);

        if (isPossiblePlace) {
            placeShip(ship, x, y, isHorizontal);
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
            if (getPoint(x - 1, y) != null && getPoint(x - 1, y ).isNotBusy()) {
                getPoint(x - 1, y ).setPointType(Point.type.BUSY);
            }
        }

    }

    private boolean checkIsPossiblePlaceShip(Ship ship, int x, int y, boolean isHorizontal) {
        boolean isPossiblePlace = true;
        for (int i = 0; i < ship.getSize(); i++) {
            if (isHorizontal) {
                if (field[x][y].getPointType() == Point.type.ALIVE || field[x][y].getPointType() == Point.type.BUSY) {
                    isPossiblePlace = false;
                    break;
                }
                if ((y - 1) >= 0 && (field[x][y - 1].getPointType() == Point.type.ALIVE || field[x][y - 1].getPointType() == Point.type.BUSY)) {
                    isPossiblePlace = false;
                    break;
                }
                if ((y + 1) < SIZE && (field[x][y + 1].getPointType() == Point.type.ALIVE || field[x][y + 1].getPointType() == Point.type.BUSY)) {
                    isPossiblePlace = false;
                    break;
                }
                if (i == 0) {
                    if ((x - 1) >= 0 && (y - 1) >= 0 && (field[x - 1][y - 1].getPointType() == Point.type.ALIVE || field[x - 1][y - 1].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }
                    if ((x - 1) >= 0 && (field[x - 1][y].getPointType() == Point.type.ALIVE || field[x - 1][y].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }
                    if ((x - 1) >= 0 && (y + 1) < SIZE && (field[x - 1][y + 1].getPointType() == Point.type.ALIVE || field[x - 1][y + 1].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }
                }
                if (i == (ship.getSize() - 1)) {
                    if ((y - 1) >= 0 && (x + 1) < SIZE && (field[x + 1][y - 1].getPointType() == Point.type.ALIVE || field[x + 1][y - 1].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }
                    if ((x + 1) < SIZE && (field[x + 1][y].getPointType() == Point.type.ALIVE || field[x + 1][y].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }
                    if ((x + 1) < SIZE && (y + 1) < SIZE && (field[x + 1][y + 1].getPointType() == Point.type.ALIVE || field[x + 1][y + 1].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }

                }
            }
            if (!isHorizontal) {
                if (field[x][y].getPointType() == Point.type.ALIVE || field[x][y].getPointType() == Point.type.BUSY) {
                    isPossiblePlace = false;
                    break;
                }
                if ((x + 1) < SIZE && (field[x + 1][y].getPointType() == Point.type.ALIVE || field[x + 1][y].getPointType() == Point.type.BUSY)) {
                    isPossiblePlace = false;
                    break;
                }
                if ((x - 1) >= 0 && (field[x - 1][y].getPointType() == Point.type.ALIVE || field[x - 1][y].getPointType() == Point.type.BUSY)) {
                    isPossiblePlace = false;
                    break;
                }
                if (i == 0) {
                    if ((x - 1) >= 0 && (y - 1) >= 0 && (field[x - 1][y - 1].getPointType() == Point.type.ALIVE || field[x - 1][y - 1].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }
                    if ((y - 1) >= 0 && (field[x][y - 1].getPointType() == Point.type.ALIVE || field[x][y - 1].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }
                    if ((x + 1) < SIZE && (y - 1) >= 0 && (field[x + 1][y - 1].getPointType() == Point.type.ALIVE || field[x + 1][y - 1].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }
                }
                if (i == (ship.getSize() - 1)) {
                    if ((x - 1) >= 0 && (y + 1) < SIZE && (field[x - 1][y + 1].getPointType() == Point.type.ALIVE || field[x - 1][y + 1].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }
                    if ((y + 1) < SIZE && (field[x][y + 1].getPointType() == Point.type.ALIVE || field[x][y + 1].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }
                    if ((x + 1) < SIZE && (y + 1) < SIZE && (field[x + 1][y + 1].getPointType() == Point.type.ALIVE || field[x + 1][y + 1].getPointType() == Point.type.BUSY)) {
                        isPossiblePlace = false;
                        break;
                    }
                }
            }
        }
        return isPossiblePlace;
    }

}
