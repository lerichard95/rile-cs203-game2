package me.lerichard;

/**
 * Created by richard on 4/15/15.
 */
public class Mob implements Actor {
    int hitPoints = FieldWorld.DEFAULT_HIT_POINTS_MAX;
    int atkLevel = FieldWorld.ATTACK_LEVEL;
    int defPower = FieldWorld.DEFENSE_LEVEL;

    public Mob(int hitPoints, int atkLevel, int defPower) {
        this.hitPoints = hitPoints;
        this.atkLevel = atkLevel;
        this.defPower = defPower;
    }

    /**
     * String representation of the Mob state
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
     * Adds HP to the Actor
     *
     * @return a new Actor with added HP
     */
    public Actor addHP(int p) {
        return new Mob(this.hitPoints + p, this.atkLevel, this.defPower);
    }

    /**
     * Removes HP from the Actor
     *
     * @return a new Actor with removed HP
     */
    public Actor removeHP(int p) {
        return new Mob(this.hitPoints - p, this.atkLevel, this.defPower);
    }

    /**
     * Returns a
     *
     * @return a dead Actor
     */
    public Actor kill() {
        return new Mob(0, this.atkLevel, this.defPower);
    }

    /**
     * Returns true if the Actor is alive or dead
     *
     * @return true if Actor is alive
     */
    public boolean isAlive() {
        return (this.hitPoints > 0);
    }
}
