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

    public Mob(int hitPoints, int atkLevel, int defPower, boolean isDef) {
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
        return new Mob(this.hitPoints + p, this.atkLevel, this.defPower, false);
    }

    /**
     * Removes HP from the Actor
     *
     * @return a new Actor with removed HP
     */
    public Mob removeHP(int p) {
        if (this.isDef) {
            int newHP = this.hitPoints - (int) (p - (p * (1 / this.defPower)));
            return new Mob(newHP, this.atkLevel, this.defPower, false);
        }
        return new Mob(this.hitPoints - p, this.atkLevel, this.defPower, false);
    }

    /**
     * Returns a dead Mob
     *
     * @return a dead Mob
     */
    public Mob kill() {
        return new Mob(0, this.atkLevel, this.defPower, false);
    }

    /**
     * Returns true if the mob is alive or dead
     *
     * @return true if Mob is alive
     */

    public boolean isAlive() {
        return (this.hitPoints > 0);
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
        return new Mob(this.hitPoints, this.atkLevel, this.defPower, true);
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
    public boolean equals(Mob that) {
        return ((this.hitPoints == that.hitPoints)
                && (this.atkLevel == that.atkLevel)
                && (this.defPower == that.defPower)
                && (this.isDef == that.isDef)
        );
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
