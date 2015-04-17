package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.WorldImage;
import java.util.ArrayList;

/**
 * Created by richard on 4/17/15.
 */
public class FieldWorld extends World {
    public static final int DEFAULT_HIT_POINTS_MAX = 100;
    public static final int ATTACK_LEVEL = 25;
    public static final int DEFENSE_LEVEL = 10;
    public static final int DEFAULT_HP_POTS = 1;

    public Player playerState = new Player(DEFAULT_HP_POTS, ATTACK_LEVEL, DEFENSE_LEVEL);
    public int hpPots = DEFAULT_HP_POTS;
    public boolean haveTreasure = false;
    ArrayList<FieldObject> fieldObjects;

    public FieldWorld(Player playerState, int hpPots, boolean haveTreasure, ArrayList<FieldObject> fieldObjects) {
        this.playerState = playerState;
        this.hpPots = hpPots;
        this.haveTreasure = haveTreasure;
        this.fieldObjects = fieldObjects;
    }




    @Override
    public WorldImage makeImage() {
        return null;
    }
}
