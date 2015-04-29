package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.CircleImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

import java.awt.*;


/**
 * FieldWorld is the game mode that represents the field map
 */
public class FieldWorld extends World {
    public static final int FIELD_OBJECT_RADIUS = 40;

    public static final int DEFAULT_HIT_POINTS_MAX = 100;
    public static final int ATTACK_LEVEL = 25;
    public static final int DEFENSE_LEVEL = 4;
    public static final int DEFAULT_HP_POTS = 1;
    public static final int HEAL_AMOUNT = 30;
    public static final int MAX_FIELD_HEIGHT = 12;
    public static final int MAX_FIELD_WIDTH = 12;

    // Persistent states
    public Player playerState = new Player(DEFAULT_HIT_POINTS_MAX, ATTACK_LEVEL, DEFENSE_LEVEL, DEFAULT_HP_POTS, false);
    public Coord treasureCoord =
            new Coord(Main.RAND.nextInt(MAX_FIELD_WIDTH),
                    Main.RAND.nextInt(MAX_FIELD_HEIGHT));
    public boolean haveTreasure = false;
    public int stepsTaken = 0;
    FieldObject fieldObjectPlayer = new FieldObject(new Coord(5, 0), FieldObjectType.PLAYER);
    FieldObject fObjTreasure = new FieldObject(treasureCoord, FieldObjectType.TREASURE);

    // TODO: Generalize the fieldObjects to objects in ArrayLists
    //ArrayList<FieldObject> fieldObjects;

    public FieldWorld(
            Player playerState,
            boolean haveTreasure,
            Coord treasureCoord,
            FieldObject fieldObjectPlayer,
            //  ArrayList<FieldObject> fieldObjects,
            int stepsTaken) {
        this.playerState = playerState;
        this.haveTreasure = haveTreasure;
        this.treasureCoord = treasureCoord;
        this.fieldObjectPlayer = fieldObjectPlayer;
        //this.fieldObjects = fieldObjects;
        this.stepsTaken = stepsTaken;
    }

    /**
     * Creates a brand-new default FieldWorld
     */
    public FieldWorld() {
    }

    /**
     * Check for treasure collision and do nothing
     *
     * @return World
     */
    @Override
    public World onTick() {
        Main.consolePrint("Player is at: " + fieldObjectPlayer.myCoords.toString());
        if (collideTreasure()) {
            Main.consolePrint("Player has collected treasure!");
            FieldWorld wholeNewWorld = new FieldWorld();
            return
                    new MessageWorld("You got the treasure! Press a key to start a new game!",
                            wholeNewWorld);
        }

        return this;
    }

    /**
     * Makes decision about whether or not a random battle should occur
     *
     * @param steps int, number of steps taken
     * @return true if battle should occur
     */
    private boolean getRandomBattle(int steps) {
        if (steps <= 0) {
            return false;
        }
        double coinFlip = Main.RAND.nextDouble();
        double battleThreshold = (1 - (1 / steps));
        return (coinFlip < battleThreshold);
    }

    /**
     * Adapter - if a battle should be entered
     *
     * @return World of the battle
     */
    public World enterPossibleBattle(World nonBattleWorld) {
        if (getRandomBattle(this.stepsTaken)) {
            // Prepare values for a random mob encounter
            int mobHP = Math.abs(Main.RAND.nextInt(
                    (int) (FieldWorld.DEFAULT_HIT_POINTS_MAX * 0.75)));
            int mobATK = Math.abs(Main.RAND.nextInt(
                    (int) (FieldWorld.ATTACK_LEVEL * 0.75)));
            int mobDEF = Math.abs(Main.RAND.nextInt(
                    (int) (FieldWorld.DEFENSE_LEVEL * 0.75)));

            Mob newMob = new Mob(mobHP, mobATK, mobDEF, false);
            BattleWorld newBattle = new BattleWorld(this, this.playerState, newMob, false);

            Main.consolePrint("Entering random battle!");
            Main.consolePrint("newMob=" + newMob.toString());
            return new MessageWorld("Random battle!", newBattle);
        }
        // Return an unmodified world when there is not a random battle
        return nonBattleWorld;
    }


    /**
     * Handles key events
     *
     * @param s the input key
     * @return World depending on what should happen
     */
    public World onKeyEvent(String s) {
        Main.consolePrint("Player pressed: " + s);
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
     * Draw the FieldWorld
     *
     * @return WorldImage
     */
    public WorldImage makeImage() {
        return new CircleImage(new Posn(10, 10), 10, Color.blue);

    }


    /**
     * Moves the player up one space
     *
     * @return a new World with a new Player FieldObject
     */
    public World movePlayerUp() {

        if (fieldObjectPlayer.myCoords.y >= MAX_FIELD_HEIGHT) {
            Main.consolePrint("Cannot move beyond lower y bound");
            return new FieldWorld(this.playerState, this.haveTreasure, this.treasureCoord,
                    this.fieldObjectPlayer, this.stepsTaken);
        }

        int newY = this.fieldObjectPlayer.myCoords.y + 1;
        Coord newCoord = new Coord(this.fieldObjectPlayer.myCoords.x, newY);
        FieldObject newFieldPlayerObject = new FieldObject(newCoord, FieldObjectType.PLAYER);
        int newStepsTaken = (this.stepsTaken + 1);
        Main.consolePrint("Moved player up, new FieldPlayerObject is" + newFieldPlayerObject.toString());
        return enterPossibleBattle(
                new FieldWorld(
                        this.playerState, this.haveTreasure, this.treasureCoord,
                        newFieldPlayerObject, newStepsTaken));
    }

    /**
     * Moves the player down one space
     *
     * @return a new World with a new Player FieldObject
     */
    public World movePlayerDown() {
        // Only move player if they are within bounds
        if (fieldObjectPlayer.myCoords.y <= 0) {
            Main.consolePrint("Cannot move player beyond lower y bound");
            return new FieldWorld(this.playerState, this.haveTreasure, this.treasureCoord,
                    this.fieldObjectPlayer, this.stepsTaken);
        }
        int newY = this.fieldObjectPlayer.myCoords.y - 1;
        Coord newCoord = new Coord(this.fieldObjectPlayer.myCoords.x, newY);
        FieldObject newFieldObjectPlayer = new FieldObject(newCoord, FieldObjectType.PLAYER);
        int newStepsTaken = (this.stepsTaken + 1);
        Main.consolePrint("Moved player down, newFieldObjectPlayer=" + newFieldObjectPlayer.toString());
        return enterPossibleBattle(
                new FieldWorld(
                        this.playerState, this.haveTreasure, this.treasureCoord,
                        newFieldObjectPlayer, newStepsTaken));
    }

    public World movePlayerLeft() {
        // Only move player if they are within bounds
        if (fieldObjectPlayer.myCoords.x <= 0) {
            Main.consolePrint("Cannot move player beyond lower x bound");
            return new FieldWorld(this.playerState, this.haveTreasure, this.treasureCoord,
                    this.fieldObjectPlayer, this.stepsTaken);
        }
        int newX = this.fieldObjectPlayer.myCoords.x - 1;
        Coord newCoord = new Coord(newX, this.fieldObjectPlayer.myCoords.y);
        FieldObject newFieldObjectPlayer = new FieldObject(newCoord, FieldObjectType.PLAYER);
        int newStepsTaken = (this.stepsTaken + 1);
        Main.consolePrint("Moved player left, newFieldObjectPlayer=" + newFieldObjectPlayer.toString());
        return enterPossibleBattle(
                new FieldWorld(this.playerState, this.haveTreasure, this.treasureCoord,
                        newFieldObjectPlayer, newStepsTaken));
    }

    public World movePlayerRight() {
        // Only move player if they are within bounds
        if (fieldObjectPlayer.myCoords.x >= MAX_FIELD_WIDTH) {
            Main.consolePrint("Cannot move player beyond upper x bound");
            return new FieldWorld(this.playerState, this.haveTreasure, this.treasureCoord,
                    this.fieldObjectPlayer, this.stepsTaken);
        }

        int newX = this.fieldObjectPlayer.myCoords.x + 1;
        Coord newCoord = new Coord(newX, this.fieldObjectPlayer.myCoords.y);
        FieldObject newFieldObjectPlayer = new FieldObject(newCoord, FieldObjectType.PLAYER);
        int newStepsTaken = (this.stepsTaken + 1);
        Main.consolePrint("Moved player right, newFieldObjectPlayer=" + newFieldObjectPlayer.toString());
        return enterPossibleBattle(new FieldWorld(
                this.playerState, this.haveTreasure, this.treasureCoord,
                newFieldObjectPlayer, newStepsTaken));
    }

    /**
     * Decides if the player has collided with treasure or not
     *
     * @return true if the player is touching treasure
     */
    public boolean collideTreasure() {
        return this.fieldObjectPlayer.myCoords.equals(treasureCoord);
    }


    /**
     * String rep of FieldWorld
     *
     * @return String
     */
    @Override
    public String toString() {
        return "FieldWorld{" +
                "playerState=" + playerState +
                ", treasureCoord=" + treasureCoord +
                ", haveTreasure=" + haveTreasure +
                ", stepsTaken=" + stepsTaken +
                ", fieldObjectPlayer=" + fieldObjectPlayer +
                ", fObjTreasure=" + fObjTreasure +
                '}';
    }

    /**
     * Checks if an object is equal to FieldWorld with matching fields... works for nulls too
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldWorld that = (FieldWorld) o;

        if (haveTreasure != that.haveTreasure) return false;
        if (stepsTaken != that.stepsTaken) return false;
        if (!playerState.equals(that.playerState)) return false;
        if (!treasureCoord.equals(that.treasureCoord)) return false;
        if (!fieldObjectPlayer.equals(that.fieldObjectPlayer)) return false;
        return fObjTreasure.equals(that.fObjTreasure);
    }

}
