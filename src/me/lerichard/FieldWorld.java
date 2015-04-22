package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.WorldImage;

import java.util.ArrayList;

/**
 * FieldWorld is the game mode that represents the field map
 */
public class FieldWorld extends World {
    public static final int FIELD_OBJECT_RADIUS = 40;

    public static final int DEFAULT_HIT_POINTS_MAX = 100;
    public static final int ATTACK_LEVEL = 25;
    public static final int DEFENSE_LEVEL = 10;
    public static final int DEFAULT_HP_POTS = 1;

    // Persistent states
    public Player playerState = new Player(DEFAULT_HP_POTS, ATTACK_LEVEL, DEFENSE_LEVEL);
    public int hpPots = DEFAULT_HP_POTS;
    public boolean haveTreasure = false;
    FieldObject fieldObjectPlayer = new FieldObject(new Coord(5, 0), FieldObjectType.PLAYER);

    ArrayList<FieldObject> fieldObjects;

    public FieldWorld(Player playerState, int hpPots, boolean haveTreasure, ArrayList<FieldObject> fieldObjects, FieldObject fieldObjectPlayer) {
        this.playerState = playerState;
        this.hpPots = hpPots;
        this.haveTreasure = haveTreasure;
        this.fieldObjects = fieldObjects;
        this.fieldObjectPlayer = fieldObjectPlayer;
    }

    /**
     * Moves the player up one space
     *
     * @return a new World with a new Player FieldObject
     */
    public World movePlayerUp() {
        int newY = this.fieldObjectPlayer.myCoords.y + 1;
        Coord newCoord = new Coord(this.fieldObjectPlayer.myCoords.x, newY);
        return new FieldWorld(this.playerState, this.hpPots, this.haveTreasure,
                this.fieldObjects, new FieldObject(newCoord, FieldObjectType.PLAYER));
        /*
        for (FieldObject ff : fieldObjects) {
            if (ff.type == FieldObjectType.MT) {
                // Make new coord
                Coord newCoord = new Coord(ff.myCoords.x, ff.myCoords.y + 1);
                ff = ff.newCoords(newCoord);
            }
        }
        */
    }

    @Override
    public WorldImage makeImage() {
        return null;
    }
}
