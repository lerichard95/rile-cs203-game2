package me.lerichard;

import javalib.worldimages.Posn;

/**
 * Pinhole is used by the Game Worlds javalib
 */
public class Pinhole {
    int x;
    int y;

    /**
     * This constructor creates a Pinhole from an existing Coord
     *
     * @param xx
     * @param yy
     */
    public Pinhole(int xx, int yy) {
        this.x = xx + (FieldWorld.FIELD_OBJECT_RADIUS / 2);
        this.y = yy + (FieldWorld.FIELD_OBJECT_RADIUS / 2);
    }

    /**
     * String rep of Pinhole
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Pinhole{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
