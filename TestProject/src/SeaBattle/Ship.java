package SeaBattle;

import java.util.ArrayList;

public class Ship {

    private int size;
    private String description;
    private ArrayList<Point> points = new ArrayList<>();
    private Placeable placeBehavior;

    public Ship(int size, String description) {
        setSize(size);
        this.description = description;
    }

    public Ship(int size, String description, Placeable placeBehavior) {
        setSize(size);
        this.description = description;
        this.placeBehavior = placeBehavior;
    }

    public Placeable getPlaceBehavior() {
        return placeBehavior;
    }

    public void setPlaceBehavior(Placeable placeBehavior) {
        this.placeBehavior = placeBehavior;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if(size <= 1)
            this.size = 1;
        else if(size >= 4)
            this.size = 4;
        else
            this.size = size;

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

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public boolean place(GameField field) {
        if(placeBehavior != null)
            return placeBehavior.place(this, field);
        else
            return false;
    }
}
