package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.WorldImage;

/**
 * Created by richard on 4/16/15.
 */
public class BattleWorld extends World {
    static int waitTime;

    FieldWorld prevWorld;
    Actor player;
    Actor mob;
    boolean playerTurn;

    public BattleWorld(FieldWorld prevWorld, Actor player, Actor mob, boolean playerTurn) {
        this.prevWorld = prevWorld;
        this.player = player;
        this.mob = mob;
        this.playerTurn = playerTurn;
    }


    /**
     * @return
     */
    public World switchboard() {
        // Only do stuff when the player and mob are alive
        while (this.player.isAlive() && this.mob.isAlive()) {

        }

        // If the mob dies, then show victory, decide to gift a potion
        if (!this.mob.isAlive()) {
            //TODO: Randomly decide if the player should get a potion
            FieldWorld newFieldWorld;
            newFieldWorld = new FieldWorld(this.player, hpPots, noTreasure, );

            return new MessageWorld(, );
        }

        // If the player dies... show game over message and allow a restart
        if (!this.player.isAlive()) {
            FieldWorld aWholeNewWorld = new FieldWorld();
            return new MessageWorld("GAME OVER! Press a key to start a new game.", aWholeNewWorld);
        }

    }


    public int damageAmount() {
        return Math.abs(
                Main.RAND.nextInt(FieldWorld.ATTACK_LEVEL));
    }


    public World actionMob() {
        int choice = Math.abs(Main.RAND.nextInt(2));
        // 0. Attack player
        if (choice == 0) {
            Actor newPlayer = this.player.removeHP(damageAmount());
            return new BattleWorld(this.prevWorld, newPlayer, this.mob, true);
        }
        // 1. Put Mob into defense mode
        else {
            Actor newMob = this.mob.activateDefend();
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


        } else {
            return this;
        }

    }

    @Override
    public WorldImage makeImage() {
        return null;
    }
}
