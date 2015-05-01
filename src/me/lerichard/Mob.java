package me.lerichard;

import javalib.worldimages.CircleImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

import java.awt.*;

/**
 * Created by richard on 4/15/15.
 */
public class Mob implements Actor {
    int hitPoints = FieldWorld.DEFAULT_HIT_POINTS_MAX;
    int atkLevel = FieldWorld.ATTACK_LEVEL;
    int defPower = FieldWorld.DEFENSE_LEVEL;
    boolean isDef = false;

    public Mob(int hitPoints, int atkLevel, boolean isDef) {
        this.hitPoints = hitPoints;
        this.atkLevel = atkLevel;
        this.defPower = defPower;
        this.isDef = isDef;
    }


    /**
     * Adds HP to the mob
     *
     * @return a new Actor with added HP
     */
    public Mob addHP(int p) {
        return new Mob(this.hitPoints + p, this.atkLevel, false);
    }

    /**
     * Removes HP from the Actor
     *
     * @return a new Actor with removed HP
     */
    public Mob removeHP(int p) {
        if (this.isDef) {
            int damage = p;
            if (p >= FieldWorld.DEFENSE_LEVEL) {
                damage = (int) (p / FieldWorld.DEFENSE_LEVEL);
            }
            int newHP = this.hitPoints - damage;
            if (Main.consoleMode) {
                Main.consolePrint("Mob defended! Actual damage:" + damage);
            }
            return new Mob(newHP, this.atkLevel, false);
        }
        int newHPP = this.hitPoints - p;
        return new Mob(newHPP, this.atkLevel, false);
    }

    /**
     * Returns a dead Mob
     *
     * @return a dead Mob
     */
    public Mob kill() {
        return new Mob(0, this.atkLevel, false);
    }

    /**
     * Returns true if the mob is alive or dead
     *
     * @return true if Mob is alive
     */

    public boolean isAlive() {
        return (this.hitPoints >= 0);
    }

    /**
     * Returns true if the Actor is currently in defense mode
     *
     * @return
     */
    public boolean isDef() {
        return this.isDef;
    }

    /**
     * Return a Mob that is in defense mode
     *
     * @return
     */
    public Mob activateDefend() {
        return new Mob(this.hitPoints, this.atkLevel, true);
    }

    /**
     * String representation of the Mob state
     *
     * @return String representing player state
     */
    @Override
    public String toString() {
        return "Mob{" +
                "hitPoints=" + hitPoints +
                ", atkLevel=" + atkLevel +
                ", defPower=" + defPower +
                ", isDef=" + isDef +
                '}';
    }

    /**
     * True if two Mobs represent the same values
     *
     * @param that Mob to compare with
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mob mob = (Mob) o;

        if (hitPoints != mob.hitPoints) return false;
        if (atkLevel != mob.atkLevel) return false;
        if (defPower != mob.defPower) return false;
        return isDef == mob.isDef;

    }

    /**
     * Returns a WorldImage representing the Mob
     *
     * @return WorldImage
     */
    public WorldImage draw() {

        return new CircleImage(new Posn(20, 20), 10, Color.blue);

    }


}
