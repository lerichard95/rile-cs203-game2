package me.lerichard;

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
     * String representation of the Mob state
     *
     * @return String representing player state
     */
    public String toString() {
        return "Mob{" +
                "hitPoints=" + hitPoints +
                ", atkLevel=" + atkLevel +
                ", defPower=" + defPower +
                '}';
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
            return new Mob(this.hitPoints - (int) (p * FieldWorld.DEFENSE_LEVEL), this.atkLevel, this.defPower, false);
        }
        return new Mob(this.hitPoints - p, this.atkLevel, this.defPower, this.isDef);
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
}
