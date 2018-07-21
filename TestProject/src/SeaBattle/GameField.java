package SeaBattle;

public class GameField {

    static final int SIZE = 10;
    private Point[][] field;

    public GameField() {

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


}
