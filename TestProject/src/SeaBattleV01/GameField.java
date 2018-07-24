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
                    System.out.print("*");
                } else if (currentPoint.getPointType() == Point.type.BUSY) {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }

    public void placeRandomShips() {
        Ship tempShip = new Ship(4, "BattleShip");
        boolean isSuccessPlaced = false;
        do {
            isSuccessPlaced = placeShip(tempShip);
        } while (!isSuccessPlaced);
    }

    public boolean placeShip(Ship ship) {
        Random random = new Random();
        boolean isHorizontal = random.nextBoolean();
        int x = isHorizontal ? random.nextInt(SIZE - ship.getSize() + 1) : random.nextInt(SIZE);
        int y = !isHorizontal ? random.nextInt(SIZE - ship.getSize() + 1) : random.nextInt(SIZE);

        Point currentPoint = getPoint(x, y);
        boolean isPossiblePlace = true;
        for (int i = 0; i < ship.getSize(); i++) {
            if (isHorizontal) {
                if (field[x][y].getPointType() == Point.type.ALIVE || field[x][y].getPointType() == Point.type.BUSY) {
                    isPossiblePlace = false;
                    break;
                }
                if (field[x][y - 1].getPointType() == Point.type.ALIVE || field[x][y - 1].getPointType() == Point.type.BUSY) {
                    isPossiblePlace = false;
                    break;
                }
                if (field[x][y + 1].getPointType() == Point.type.ALIVE || field[x][y + 1].getPointType() == Point.type.BUSY) {
                    isPossiblePlace = false;
                    break;
                }
                if (i == 0) {
                    if (field[x - 1][y - 1].getPointType() == Point.type.ALIVE || field[x - 1][y - 1].getPointType() == Point.type.BUSY) {
                        isPossiblePlace = false;
                        break;
                    }
                    if (field[x - 1][y].getPointType() == Point.type.ALIVE || field[x - 1][y].getPointType() == Point.type.BUSY) {
                        isPossiblePlace = false;
                        break;
                    }
                    if (field[x - 1][y + 1].getPointType() == Point.type.ALIVE || field[x - 1][y + 1].getPointType() == Point.type.BUSY) {
                        isPossiblePlace = false;
                        break;
                    }
                }
                if (i == (ship.getSize() - 1)) {
                    if (field[x + 1][y - 1].getPointType() == Point.type.ALIVE || field[x + 1][y - 1].getPointType() == Point.type.BUSY) {
                        isPossiblePlace = false;
                        break;
                    }
                    if (field[x + 1][y].getPointType() == Point.type.ALIVE || field[x + 1][y].getPointType() == Point.type.BUSY) {
                        isPossiblePlace = false;
                        break;
                    }
                    if (field[x + 1][y + 1].getPointType() == Point.type.ALIVE || field[x + 1][y + 1].getPointType() == Point.type.BUSY) {
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
                if (field[x + 1][y].getPointType() == Point.type.ALIVE || field[x + 1][y].getPointType() == Point.type.BUSY) {
                    isPossiblePlace = false;
                    break;
                }
                if (field[x - 1][y].getPointType() == Point.type.ALIVE || field[x - 1][y].getPointType() == Point.type.BUSY) {
                    isPossiblePlace = false;
                    break;
                }
                if (i == 0) {
                    if (field[x - 1][y - 1].getPointType() == Point.type.ALIVE || field[x - 1][y - 1].getPointType() == Point.type.BUSY) {
                        isPossiblePlace = false;
                        break;
                    }
                    if (field[x][y - 1].getPointType() == Point.type.ALIVE || field[x][y - 1].getPointType() == Point.type.BUSY) {
                        isPossiblePlace = false;
                        break;
                    }
                    if (field[x + 1][y - 1].getPointType() == Point.type.ALIVE || field[x + 1][y - 1].getPointType() == Point.type.BUSY) {
                        isPossiblePlace = false;
                        break;
                    }
                }
                if (i == (ship.getSize() - 1)) {
                    if (field[x - 1][y + 1].getPointType() == Point.type.ALIVE || field[x - 1][y + 1].getPointType() == Point.type.BUSY) {
                        isPossiblePlace = false;
                        break;
                    }
                    if (field[x][y + 1].getPointType() == Point.type.ALIVE || field[x][y + 1].getPointType() == Point.type.BUSY) {
                        isPossiblePlace = false;
                        break;
                    }
                    if (field[x + 1][y + 1].getPointType() == Point.type.ALIVE || field[x + 1][y + 1].getPointType() == Point.type.BUSY) {
                        isPossiblePlace = false;
                        break;
                    }

                }
            }


        }

        return true;
    }


}
