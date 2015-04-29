package me.lerichard;

/**
 * Created by richard on 4/15/15.
 */
public class Player implements Actor {
    int hitPoints = FieldWorld.DEFAULT_HIT_POINTS_MAX;
    int atkLevel = FieldWorld.ATTACK_LEVEL;
    int defPower = FieldWorld.DEFENSE_LEVEL;
    int hpPots = FieldWorld.DEFAULT_HP_POTS;
    boolean isDef = false;


    public Player(int hp, int atk, int def, int hpPots, boolean isDef) {
        this.hitPoints = hp;
        this.atkLevel = atk;
        this.defPower = def;
        this.hpPots = hpPots;
        this.isDef = isDef;
    }

    /**
     * String representation of Player state
     *
     * @return String representing Player state
     */
    @Override
    public String toString() {
        return "Player{" +
                "hitPoints=" + hitPoints +
                ", atkLevel=" + atkLevel +
                ", defPower=" + defPower +
                ", hpPots=" + hpPots +
                ", isDef=" + isDef +
                '}';
    }

    /**
     * Adds HP to the player
     *
     * @param p HP to add
     * @return a new Actor with added HP
     */
    public Player addHP(int p) {

        return new Player(this.hitPoints + p, this.atkLevel, this.defPower, this.hpPots, false);
    }

    /**
     * Removes HP from the player
     *
     * @return a new Actor with removed HP
     */
    public Player removeHP(int p) {
        if (this.isDef) {
            int newHP = this.hitPoints - (int) (p * FieldWorld.DEFENSE_LEVEL);
            return new Player(newHP, this.atkLevel, this.defPower, this.hpPots, true);
        }
        return new Player(this.hitPoints - p, this.atkLevel, this.defPower, this.hpPots, this.isDef);
    }

    /**
     * Returns
     *
     * @return a dead player
     */
    public Player kill() {

        return new Player(0, this.atkLevel, this.defPower, this.hpPots, false);
    }

    /**
     * Returns true if the player is alive or dead
     *
     * @return true if player is alive
     */
    public boolean isAlive() {
        return (this.hitPoints > 0);
    }

    /**
     * Returns true if the player is currently in defense mode
     *
     * @return Returns true if the player is currently in defense mode
     */
    public boolean isDef() {
        return this.isDef;
    }

    /**
     * Return a Player that is in defense mode
     *
     * @return
     */
    public Player activateDefend() {
        return new Player(this.hitPoints, this.atkLevel, this.defPower, this.hpPots, true);
    }

    /**
     * True if two Players represent the same data
     *
     * @return boolean
     */
    public boolean equals(Player that) {
        return
                ((this.hitPoints == that.hitPoints)
                        && (this.atkLevel == that.atkLevel)
                        && (this.defPower == that.defPower)
                        && (this.hpPots == that.hpPots)
                        && (this.isDef == that.isDef)
                );
    }
}
