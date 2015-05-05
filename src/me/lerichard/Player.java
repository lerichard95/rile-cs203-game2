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


    public Player(int hp, int atk, int hpPots, boolean isDef) {
        this.hitPoints = hp;
        this.atkLevel = atk;
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
        Main.consolePrint("Amount healed=" + p);
        int newPots = this.hpPots;
        //Only decrement hpPots if it is greater than 0
        if (this.hpPots > 0) {
            newPots = this.hpPots - 1;
        }

        // Strategic: only heal up to the specifed maximum HP... discard excess
        int newHP = this.hitPoints + p;
        if (newHP <= FieldWorld.DEFAULT_HIT_POINTS_MAX) {
            return new Player(newHP, this.atkLevel, newPots, false);
        }
        return new Player(FieldWorld.DEFAULT_HIT_POINTS_MAX, this.atkLevel, newPots, false);
    }

    /**
     * Removes HP from the player
     *
     * @return a new Actor with removed HP
     */

    public Player removeHP(int p) {
        if (this.isDef) {
            int damage = p;
            // Only apply damage buffer if necessary
            if (p >= FieldWorld.DEFENSE_LEVEL) {
                damage = (int) (p / FieldWorld.DEFENSE_LEVEL);
            }
            int newHP = this.hitPoints - damage;
            if (Main.consoleMode) {
                Main.consolePrint("Player defended! Actual damage:" + damage);
            }
            return new Player(newHP, this.atkLevel, this.hpPots, false);
        }
        return new Player(this.hitPoints - p, this.atkLevel, this.hpPots, false);
    }

    /**
     * Returns
     *
     * @return a dead player
     */
    public Player kill() {

        return new Player(0, this.atkLevel, this.hpPots, false);
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
        return new Player(this.hitPoints, this.atkLevel, this.hpPots, true);
    }

    /**
     * True if two Players represent the same data
     *
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (hitPoints != player.hitPoints) return false;
        if (atkLevel != player.atkLevel) return false;
        if (defPower != player.defPower) return false;
        if (hpPots != player.hpPots) return false;
        return isDef == player.isDef;

    }
}
