package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.WorldImage;



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
    public int stepsTaken = 0;

    //TODO: generate a random coord for the treasure
    public Coord treasureCoord =
            new Coord(Main.RAND.nextInt(MAX_FIELD_WIDTH),
                    Main.RAND.nextInt(MAX_FIELD_HEIGHT));

    // Persistent states
    public Player playerState = new Player(DEFAULT_HIT_POINTS_MAX, ATTACK_LEVEL, DEFENSE_LEVEL, DEFAULT_HP_POTS, false);

    public boolean haveTreasure = false;
    FieldObject fieldObjectPlayer = new FieldObject(new Coord(5, 0), FieldObjectType.PLAYER);
    FieldObject fObjTreasure = new FieldObject(treasureCoord, FieldObjectType.TREASURE);

    // TODO: Generalize the fieldObjects to a bunch of ArrayLists
    // ArrayList<FieldObject> fieldObjects;

    // TODO: player state will hold the hpPots instead...
    public FieldWorld(
            Player playerState,
            boolean haveTreasure,
            FieldObject fieldObjectPlayer,
            int stepsTaken) {
        this.playerState = playerState;
        this.haveTreasure = haveTreasure;
        this.fieldObjectPlayer = fieldObjectPlayer;
        this.stepsTaken = stepsTaken;
    }

    /**
     * Creates a brand-new default FieldWorld
     */
    public FieldWorld() {
    }

    /**
     * @return World
     */
    @Override
    public World onTick() {

        return super.onTick();
    }

    /**
     * Makes decision about whether or not a random battle should occur
     *
     * @param steps int, number of steps taken
     * @return true if battle should occur
     */
    private boolean getRandomBattle(int steps) {
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

            return new MessageWorld("Random battle!", new BattleWorld(this.playerState, newMob));
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
                    this.fieldObjects, this.fieldObjectPlayer, (this.stepsTaken + 1));
        }

        int newY = this.fieldObjectPlayer.myCoords.y + 1;
        Coord newCoord = new Coord(this.fieldObjectPlayer.myCoords.x, newY);
        return enterPossibleBattle(
                new FieldWorld(this.playerState, this.hpPots, this.haveTreasure,
                        this.fieldObjects, new FieldObject(newCoord, FieldObjectType.PLAYER), (this.stepsTaken + 1)));
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
                    this.fieldObjects, this.fieldObjectPlayer, (this.stepsTaken + 1));
        }
        int newY = this.fieldObjectPlayer.myCoords.y - 1;
        Coord newCoord = new Coord(this.fieldObjectPlayer.myCoords.x, newY);
        return enterPossibleBattle(
                new FieldWorld(
                        this.playerState, this.hpPots, this.haveTreasure, this.fieldObjects,
                        new FieldObject(newCoord, FieldObjectType.PLAYER), (this.stepsTaken + 1)));
    }

    public World movePlayerLeft() {
        // Only move player if they are within bounds
        if (fieldObjectPlayer.myCoords.x <= 0) {
            return new FieldWorld(this.playerState, this.hpPots, this.haveTreasure,
                    this.fieldObjects, this.fieldObjectPlayer, (this.stepsTaken + 1));
        }
        int newX = this.fieldObjectPlayer.myCoords.x - 1;
        Coord newCoord = new Coord(newX, this.fieldObjectPlayer.myCoords.y);

        return enterPossibleBattle(
                new FieldWorld(
                        this.playerState, this.hpPots, this.haveTreasure, this.fieldObjects,
                        new FieldObject(newCoord, FieldObjectType.PLAYER), (this.stepsTaken + 1)));
    }

    public World movePlayerRight() {
        // Only move player if they are within bounds
        if (fieldObjectPlayer.myCoords.x >= MAX_FIELD_WIDTH) {
            return enterPossibleBattle(
                    new FieldWorld(
                            this.playerState, this.hpPots, this.haveTreasure,
                            this.fieldObjects, this.fieldObjectPlayer, (this.stepsTaken + 1)));
        }

        int newX = this.fieldObjectPlayer.myCoords.x + 1;
        Coord newCoord = new Coord(newX, this.fieldObjectPlayer.myCoords.y);

        return enterPossibleBattle(
                new FieldWorld(
                        this.playerState, this.haveTreasure,
                        new FieldObject(newCoord, FieldObjectType.PLAYER), (this.stepsTaken + 1)));
    }

    @Override
    public WorldImage makeImage() {
        return null;
    }
}
