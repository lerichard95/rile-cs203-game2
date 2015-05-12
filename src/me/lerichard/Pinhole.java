package me.lerichard;

import javalib.worldimages.Posn;

/**
 * Pinhole is used by the Game Worlds javalib
 */
public class Pinhole extends Posn {
    int x;
    int y;

    /**
     * This constructor creates a Pinhole from an existing X/Y set representing coords.
     *
     * @param xx
     * @param yy
     */
    public Pinhole(int xx, int yy) {
        super((xx * FieldWorld.FIELD_OBJECT_DIAMETER) + FieldWorld.FIELD_OBJECT_RADIUS,
                (yy * FieldWorld.FIELD_OBJECT_DIAMETER) +
                        FieldWorld.FIELD_OBJECT_RADIUS);
        this.x = (xx * FieldWorld.FIELD_OBJECT_DIAMETER) + FieldWorld.FIELD_OBJECT_RADIUS;
        this.y = (yy * FieldWorld.FIELD_OBJECT_DIAMETER) + FieldWorld.FIELD_OBJECT_RADIUS;

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

    /**
     * Returns true if two Pinholes represent identical points
     *
     * @param that Pinhole to be compared to
     * @return true if Pinholes are identical
     */
    public boolean equals(Pinhole that) {
        return ((this.x == that.x) && (this.y == that.y));
    }
}
