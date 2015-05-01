package me.lerichard;

import javalib.worldimages.Posn;

/**
 * Coord represents an X/Y coordinate.
 * Created by richard on 4/17/15.
 */
public class Coord extends Posn {
    int x;
    int y;

    public Coord(int x, int y) {
        // What does super actually do in this context??
        super(x, y);
        this.x = x;
        this.y = y;
    }


    /**
     * Returns true if this coord equals the other coord
     *
     * @param that other Coord to be compared to
     * @return true if the x and y fields match
     */
    public boolean equals(Coord that) {
        return ((this.x == that.x) && (this.y == that.y));
    }

    /**
     * Calculates the pinhole from a coord
     *
     * @return Pinhole representing the converted X Y values
     */
    public Pinhole CoordToPinhole() {
        return new Pinhole(this.x, this.y);
    }


    /**
     * String rep of coord
     *
     * @return
     */
    @Override
    public String toString() {
        return "Coord{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


}
