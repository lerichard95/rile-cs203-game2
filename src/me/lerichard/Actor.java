package me.lerichard;

import javalib.worldimages.CircleImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

import java.awt.*;

/**
 * Created by richard on 4/15/15.
 */
public class Actor {
    int hitPoints = FieldWorld.DEFAULT_HIT_POINTS_MAX;
    int atkLevel = FieldWorld.ATTACK_LEVEL;
    int defPower = FieldWorld.DEFENSE_LEVEL;
    int hpPots = FieldWorld.DEFAULT_HP_POTS;
    boolean isDef = false;
    ActorType type;


    public Actor(int hp, int atk, int defPower, boolean isDef, ActorType type, int hpPots) {
        this.hitPoints = hp;
        this.atkLevel = atk;
        this.defPower = defPower;
        this.hpPots = hpPots;
        this.isDef = isDef;
        this.type = type;
    }

    /**
     * Generates a new Actor with random stats
     *
     * @param type ActorType of the new Actor
     * @return A new randomActor
     */
    public static Actor randomActor(ActorType type) {
        int newHP = Math.abs(Main.RAND.nextInt(
                (FieldWorld.DEFAULT_HIT_POINTS_MAX)));

        int newATK = Math.abs(Main.RAND.nextInt(
                (FieldWorld.ATTACK_LEVEL)));

        //Might divide by zero if the defense ends up being zero
        int newDEF = Math.abs(2 + Main.RAND.nextInt(FieldWorld.DEFENSE_LEVEL));

        if (type.equals(ActorType.MOB)) {
            //Mob should have up to 75% of the player's stats.
            newHP = Math.abs(Main.RAND.nextInt(
                    (int) (FieldWorld.DEFAULT_HIT_POINTS_MAX * 0.75)));

            newATK = Math.abs(Main.RAND.nextInt(
                    (int) (FieldWorld.ATTACK_LEVEL * 0.75)));

            //Might divide by zero if the defense ends up being zero
            newDEF = Math.abs(2 + Main.RAND.nextInt(
                    (int) (FieldWorld.DEFENSE_LEVEL * 0.75)));
        }

        //Actor should not be in defense mode upon start
        return new Actor(newHP, newATK, newDEF, false, type, 0);
    }

    /**
     * String representation of Actor state
     *
     * @return String representing Actor state
     */
    @Override
    public String toString() {
        return "Actor{" +
                "hitPoints=" + hitPoints +
                ", atkLevel=" + atkLevel +
                ", defPower=" + defPower +
                ", hpPots=" + hpPots +
                ", isDef=" + isDef +
                '}';
    }

    /**
     * Adds HP to the Actor
     *
     * @param p HP to add
     * @return a new Actor with added HP
     */
    public Actor addHP(int p) {
        int newPots = this.hpPots;
        if (this.hpPots > 0) {
            //Only decrement hpPots if it is greater than 0
            newPots = this.hpPots - 1;

            // Strategic: only heal up to the specifed maximum HP... discard excess
            int newHP = this.hitPoints + p;
            if (newHP <= FieldWorld.DEFAULT_HIT_POINTS_MAX) {
                Main.consolePrint("Amount healed=" + p);
                return new Actor(newHP, this.atkLevel, this.defPower, false, this.type, newPots);
            }
            Main.consolePrint("Amount healed=" + p);
            return new Actor(FieldWorld.DEFAULT_HIT_POINTS_MAX, this.atkLevel, this.defPower, false, this.type, newPots);
        } else {
            // Don't do anything if the player didn't have any potions
            Main.consolePrint("No potions available— did not heal");
            return new Actor(this.hitPoints, this.atkLevel, this.defPower, false, this.type, this.hpPots);
        }
    }

    /**
     * Removes HP from the player
     *
     * @return a new Actor with removed HP
     */
    public Actor removeHP(int p) {
        if (this.isDef) {
            int damage = p;
            // Only apply damage buffer if necessary
            if (p >= this.defPower) {
                damage = (int) (p / this.defPower);
            }
            int newHP = this.hitPoints - damage;
            if (Main.consoleMode) {
                if (this.type.equals(ActorType.PLAYER)) {
                    Main.consolePrint("Player defended! Actual damage:" + damage);
                } else
                    Main.consolePrint("Mob defended! Actual damage:" + damage);

            }
            return new Actor(newHP, this.atkLevel, this.defPower, false, this.type, this.hpPots);
        }
        return new Actor(this.hitPoints - p, this.atkLevel, this.defPower, false, this.type, this.hpPots);
    }

    /**
     * Returns
     *
     * @return a dead Actor
     */
    public Actor kill() {

        return new Actor(0, this.atkLevel, this.defPower, false, this.type, this.hpPots);
    }

    /**
     * Returns true if the Actor is alive or dead
     *
     * @return true if Actor is alive
     */
    public boolean isAlive() {
        return (this.hitPoints > 0);
    }

    /**
     * Returns true if the Actor is currently in defense mode
     *
     * @return Returns true if the Actor is currently in defense mode
     */
    public boolean isDef() {
        return this.isDef;
    }

    /**
     * Return a Actor that is in defense mode
     *
     * @return
     */
    public Actor activateDefend() {
        return new Actor(this.hitPoints, this.atkLevel, this.defPower, true, this.type, this.hpPots
        );
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

        Actor player = (Actor) o;

        if (hitPoints != player.hitPoints) return false;
        if (atkLevel != player.atkLevel) return false;
        if (defPower != player.defPower) return false;
        if (hpPots != player.hpPots) return false;
        return isDef == player.isDef;
    }

    /**
     * Returns a WorldImage representing the Actor in Battle mode
     *
     * @return WorldImage
     */
    public WorldImage draw() {
        if (this.type.equals(ActorType.PLAYER)) {
// TODO: Draw special image for Player
            return new CircleImage(new Posn(20, 20), 10, Color.blue);
        } else
// TODO: Draw special image for Mob
            return new CircleImage(new Posn(20, 20), 10, Color.blue);

    }

}
