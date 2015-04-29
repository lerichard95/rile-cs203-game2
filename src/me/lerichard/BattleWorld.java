package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.WorldImage;

/**
 * Created by richard on 4/16/15.
 */
public class BattleWorld extends World {
    static int waitTime;

    FieldWorld prevWorld;
    Player player;
    Mob mob;
    boolean playerTurn;

    public BattleWorld(FieldWorld prevWorld, Player player, Mob mob, boolean playerTurn) {
        this.prevWorld = prevWorld;
        this.player = player;
        this.mob = mob;
        this.playerTurn = playerTurn;
    }


    /**
     * @return
     */
    public World onTick() {
        // Only do stuff when the player and mob are alive

        // If the mob dies, then show victory, decide to gift a potion
        if (!this.mob.isAlive()) {

            //1 in (n -1) chance that player will get a potion...
            int n = 3;
            int randNumber = Main.RAND.nextInt(n);

            // 1. Player gets potion
            if (randNumber == 0) {
                FieldWorld newFieldWorld;
                int newPots = this.player.hpPots + 1;
                Player newPlayerState =
                        new Player(this.player.hitPoints,
                                this.player.atkLevel, this.player.defPower, newPots, false);

                newFieldWorld = new FieldWorld(
                        newPlayerState, prevWorld.haveTreasure,
                        prevWorld.treasureCoord, prevWorld.fieldObjectPlayer, 0);

                return new MessageWorld("Victory!", newFieldWorld);
            }
            // 1. Player doesn't get potion
            else {
                FieldWorld newFieldWorld = new FieldWorld(
                        this.player, prevWorld.haveTreasure,
                        prevWorld.treasureCoord, prevWorld.fieldObjectPlayer, 0);
                return new MessageWorld("Victory!", newFieldWorld);
            }
        }

        // If the player dies... show game over message and allow a restart
        if (!this.player.isAlive()) {
            FieldWorld aWholeNewWorld = new FieldWorld();
            return new MessageWorld("GAME OVER! Press a key to start a new game.", aWholeNewWorld);
        }

        // GAME ACTION:
        // when it is not the player's turn, do the mob action
        if (!this.playerTurn) {
            return actionMob();
        }

        // Don't do anything if it's the player's turn— await keypress
        return this;

    }


    public int damageAmount() {
        return Math.abs(
                Main.RAND.nextInt(FieldWorld.ATTACK_LEVEL));
    }


    public World actionMob() {
        int choice = Math.abs(Main.RAND.nextInt(2));
        // 0. Attack player
        if (choice == 0) {
            Player newPlayer = this.player.removeHP(damageAmount());
            return new BattleWorld(this.prevWorld, newPlayer, this.mob, true);
        }
        // 1. Put Mob into defense mode
        else {
            Mob newMob = this.mob.activateDefend();
            return new BattleWorld(this.prevWorld, this.player, newMob, true);
        }
    }


    /**
     * Event handler for keypresses
     *
     * @param s
     * @return
     */
    public World onKeyEvent(String s) {

        // Only accept key input when it is the player's turn
        if (this.playerTurn) {

            // Player ATTACKs
            if (s.equalsIgnoreCase("A")) {
                Mob nm = this.mob.removeHP(damageAmount());
                BattleWorld nbw = new BattleWorld(this.prevWorld, this.player, nm, false);
                return nbw;
            }

            // Player DEFENDS
            if (s.equalsIgnoreCase("D")) {
                Player np = this.player.activateDefend();
                BattleWorld nbwDef = new BattleWorld(this.prevWorld, np, this.mob, false);
                return nbwDef;
            }

            if (s.equalsIgnoreCase("P")) {
                Player npHeal = this.player.addHP(FieldWorld.HEAL_AMOUNT);
                BattleWorld nbwHeal = new BattleWorld(this.prevWorld, npHeal, this.mob, false);
                return nbwHeal;

            }

        }
        return this;
    }

    @Override
    public WorldImage makeImage() {
        return null;
    }

    /**
     * String rep of BattleWorld
     * @return String
     */
    @Override
    public String toString() {
        return "BattleWorld{" +
                "prevWorld=" + prevWorld +
                ", player=" + player +
                ", mob=" + mob +
                ", playerTurn=" + playerTurn +
                '}';
    }
}
