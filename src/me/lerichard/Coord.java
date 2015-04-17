package me.lerichard;

/**
 * Coord represents an X/Y coordinate.
 * Created by richard on 4/17/15.
 */
public class Coord {
    int x;
    int y;

    public Coord(int x, int y) {
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

}
