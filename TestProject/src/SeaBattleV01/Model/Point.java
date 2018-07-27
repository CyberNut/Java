package SeaBattleV01.Model;

public class Point {
    public static enum type {EMPTY, ALIVE, DEAD, BUSY}

    private int x;
    private int y;
    private type pointType;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.pointType = type.EMPTY;
    }

    public Point(int x, int y, type pointType) {
        this.x = x;
        this.y = y;
        this.pointType = pointType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public type getPointType() {
        return pointType;
    }

    public void setPointType(type pointType) {
        this.pointType = pointType;
    }

    public boolean isNotBusy() {
        if(pointType != type.ALIVE && pointType != type.DEAD) {
            return true;
        } else {
            return false;
        }
    }


}
