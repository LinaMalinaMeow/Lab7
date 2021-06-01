package object;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Float x; //больше -836
    private Float y;


    public Coordinates(Float x, Float y) {
        this.x = x;
        this.y = y;

        }

    /**
     *
     * @return coordinates
     */

    public Float getX() {
        return this.x;
    }

    public Float getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}