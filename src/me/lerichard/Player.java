package me.lerichard;

/**
 * Created by richard on 4/15/15.
 */
public class Player implements Actor {
    int hitPoints = FieldWorld.DEFAULT_HIT_POINTS_MAX;
    int atkLevel = FieldWorld.ATTACK_LEVEL;
    int defPower = FieldWorld.DEFENSE_LEVEL;

    public Player(int hp, int atk, int def) {
        this.hitPoints = hp;
        this.atkLevel = atk;
        this.defPower = def;
    }

    /**
     * Adds HP to the Actor
     *
     * @param p HP to add
     * @return a new Actor with added HP
     */
    public Actor addHP(int p) {
        return new Player(this.hitPoints + p, this.atkLevel, this.defPower);
    }

    /**
     * String representation of Player state
     * @return String representing Player state
     */
    public String toString() {
        return "Player{" +
                "hitPoints=" + hitPoints +
                ", atkLevel=" + atkLevel +
                ", defPower=" + defPower +
                '}';
    }

    /**
     * Removes HP from the Actor
     *
     * @return a new Actor with removed HP
     */
    public Actor removeHP(int p) {
        return new Player(this.hitPoints - p, this.atkLevel, this.defPower);
    }

    /**
     * Returns
     *
     * @return a dead Actor
     */
    public Actor kill() {
        return new Player(0, this.atkLevel, this.defPower);
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
