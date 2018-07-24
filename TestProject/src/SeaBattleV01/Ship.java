package SeaBattleV01;

import SeaBattle.Placeable;

import java.util.ArrayList;

public class Ship {

    private int size;
    private String description;


    private ArrayList<Point> points = new ArrayList<>();

    public Ship(int size, String description) {
        setSize(size);
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size <= 1) {
            this.size = 1;
        } else if (size >= 4) {
            this.size = 4;
        } else {
            this.size = size;
        }


    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        if(points.size() < size) {
            points.add(point);
        }
    }

}
