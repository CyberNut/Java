package SeaBattle;

public class GameField {

    static final int SIZE = 10;
    private Point[][] field;

    public GameField() {

        field = new Point[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = new Point(i, j);
            }
        }
    }

    public Point getPoint(int x, int y)
    {
        return field[x][y];
    }

    public void setPoint(Point point)
    {
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
                }
                else if(currentPoint.getPointType() == Point.type.ALIVE) {
                    System.out.print("" + currentPoint.getShip().getSize());
                }
                else if(currentPoint.getPointType() == Point.type.DEAD) {
                        System.out.print("*");
                }
            }
            System.out.println();

        }
    }



}
