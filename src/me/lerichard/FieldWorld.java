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
    public static final int MAX_FIELD_HEIGHT = 12;
    public static final int MAX_FIELD_WIDTH = 12;
    //TODO: generate a random coord for the treasure
    public Coord treasureCoord =
            new Coord(Main.RAND.nextInt(MAX_FIELD_WIDTH),
                    Main.RAND.nextInt(MAX_FIELD_HEIGHT));

    // Persistent states
    public Player playerState = new Player(DEFAULT_HP_POTS, ATTACK_LEVEL, DEFENSE_LEVEL);
    public int hpPots = DEFAULT_HP_POTS;
    public boolean haveTreasure = false;
    FieldObject fieldObjectPlayer = new FieldObject(new Coord(5, 0), FieldObjectType.PLAYER);
    ArrayList<FieldObject> fieldObjects;

    public FieldWorld(Player playerState, int hpPots, boolean haveTreasure,
                      ArrayList<FieldObject> fieldObjects, FieldObject fieldObjectPlayer) {
        this.playerState = playerState;
        this.hpPots = hpPots;
        this.haveTreasure = haveTreasure;
        this.fieldObjects = fieldObjects;
        this.fieldObjectPlayer = fieldObjectPlayer;
    }

    /**
     * Handles key events
     * @param s the input key
     * @return World depending on what should happen
     */

    public World onKeyEvent(String s) {
        if (s.equals("up")) {
            return movePlayerUp();
        }
        if (s.equals("down")) {
            return movePlayerDown();
        }
        if (s.equals("left")) {
            return movePlayerLeft();
        }
        if (s.equals("right")) {
            return movePlayerRight();
        }
    return this;
    }



    /**
     * Moves the player up one space
     *
     * @return a new World with a new Player FieldObject
     */
    public World movePlayerUp() {

        if (fieldObjectPlayer.myCoords.y >= MAX_FIELD_HEIGHT) {
            return new FieldWorld(this.playerState, this.hpPots, this.haveTreasure,
                    this.fieldObjects, this.fieldObjectPlayer);
        }

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

    /**
     * Moves the player down one space
     *
     * @return a new World with a new Player FieldObject
     */
    public World movePlayerDown() {
        // Only move player if they are within bounds
        if (fieldObjectPlayer.myCoords.y <= 0) {
            return new FieldWorld(this.playerState, this.hpPots, this.haveTreasure,
                    this.fieldObjects, this.fieldObjectPlayer);
        }
        int newY = this.fieldObjectPlayer.myCoords.y - 1;
        Coord newCoord = new Coord(this.fieldObjectPlayer.myCoords.x, newY);
        return new FieldWorld(
                this.playerState, this.hpPots, this.haveTreasure, this.fieldObjects,
                new FieldObject(newCoord, FieldObjectType.PLAYER));
    }

    public World movePlayerLeft() {
        // Only move player if they are within bounds
        if (fieldObjectPlayer.myCoords.x <= 0) {
            return new FieldWorld(this.playerState, this.hpPots, this.haveTreasure,
                    this.fieldObjects, this.fieldObjectPlayer);
        }
        int newX = this.fieldObjectPlayer.myCoords.x - 1;
        Coord newCoord = new Coord(newX, this.fieldObjectPlayer.myCoords.y);

        return new FieldWorld(
                this.playerState, this.hpPots, this.haveTreasure, this.fieldObjects,
                new FieldObject(newCoord, FieldObjectType.PLAYER));
    }

    public World movePlayerRight() {
        // Only move player if they are within bounds
        if (fieldObjectPlayer.myCoords.x >= MAX_FIELD_WIDTH) {
            return new FieldWorld(
                    this.playerState, this.hpPots, this.haveTreasure,
                    this.fieldObjects, this.fieldObjectPlayer);
        }

        int newX = this.fieldObjectPlayer.myCoords.x + 1;
        Coord newCoord = new Coord(newX, this.fieldObjectPlayer.myCoords.y);

        return new FieldWorld(
                this.playerState, this.hpPots, this.haveTreasure, this.fieldObjects,
                new FieldObject(newCoord, FieldObjectType.PLAYER));
    }

    @Override
    public WorldImage makeImage() {
        return null;
    }
}
