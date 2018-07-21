package SeaBattle;

public class Gamer implements Shootable {

    private String name;
    private Shootable shootBehavior;

    public Gamer(String name, Shootable shootBehavior) {
        this.name = name;
        this.shootBehavior = shootBehavior;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shootable getShootBehavior() {
        return shootBehavior;
    }

    public void setShootBehavior(Shootable shootBehavior) {
        this.shootBehavior = shootBehavior;
    }

    @Override
    public boolean doShoot(GameField field, int x, int y) {
        return shootBehavior.doShoot(field, x, y);
    }
}
