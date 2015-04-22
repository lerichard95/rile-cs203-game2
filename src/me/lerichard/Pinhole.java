package me.lerichard;

/**
 * Pinhole is used by the Game Worlds javalib
 */
public class Pinhole {
    int x;
    int y;

    public Pinhole(int xx, int yy) {
        this.x = xx + (FieldWorld.FIELD_OBJECT_RADIUS / 2);
        this.y = yy + (FieldWorld.FIELD_OBJECT_RADIUS / 2) ;
    }

}
