package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.CircleImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

import java.awt.*;
import java.util.Objects;

/**
 * Created by richard on 4/16/15.
 */
public class BattleWorld extends World {
    static int waitTime;

    FieldWorld prevWorld;
    Player player;
    Mob mob;
    boolean playerTurn;
    int ticks;

    public BattleWorld(int ticks, FieldWorld prevWorld, Player player, Mob mob, boolean playerTurn) {
        this.ticks = ticks;
        this.prevWorld = prevWorld;
        this.player = player;
        this.mob = mob;
        this.playerTurn = playerTurn;
    }


    /**
     * Controls main BattleWorld game logic. Exits to field and rewards player
     *
     * @return
     */
    @Override
    public World onTick() {
        if (Main.consoleMode && (this.ticks < Main.SHOW_MESSAGE_FOR_N_TICKS)) {
            Main.consolePrint("======   BATTLE WORLD    ======");
        }


        // Only do stuff when the player and mob are alive

        // If the mob dies, then show victory, decide to gift a potion
        if (!this.mob.isAlive()) {
            Main.consolePrint("Player wins battle!");
            //1 in (n -1) chance that player will get a potion...
            int n = 3;
            int randNumber = Main.RAND.nextInt(n);

            // 1. Player gets potion
            if (randNumber == 0) {
                FieldWorld newFieldWorld;
                int newPots = this.player.hpPots + 1;
                Player newPlayerState =
                        new Player(this.player.hitPoints,
                                this.player.atkLevel, newPots, false);
                newFieldWorld = new FieldWorld(0,
                        newPlayerState, prevWorld.haveTreasure,
                        prevWorld.treasureCoord, prevWorld.fieldObjectPlayer, 0);

                Main.consolePrint("Player got a potion!");
                return new MessageWorld(0, "Victory!", newFieldWorld);
            }
            // 1. Player doesn't get potion
            else {
                Main.consolePrint("Player didn't receive potion. Now returning to FieldWorld...");
                FieldWorld newFieldWorld = new FieldWorld(0,
                        this.player, prevWorld.haveTreasure,
                        prevWorld.treasureCoord, prevWorld.fieldObjectPlayer, 0);
                return new MessageWorld(0, "Victory!", newFieldWorld);
            }
        }

        // If the player dies... show game over message and allow a restart
        if (!this.player.isAlive()) {
            Main.consolePrint("Player died. Presenting a MessageWorld");
            FieldWorld aWholeNewWorld = new FieldWorld();
            return new MessageWorld(0, "GAME OVER! Press a key to start a new game.", aWholeNewWorld);
        }

        // GAME ACTION:
        // when it is not the player's turn, do the mob action
        if (!this.playerTurn) {
            Main.consolePrint("Mob's turn:");
            return actionMob();
        }


        if (Main.consoleMode && (this.playerTurn) && (this.ticks < Main.SHOW_MESSAGE_FOR_N_TICKS)) {
            Main.consolePrint("player=" + this.player.toString());
            Main.consolePrint("mob=" + this.mob.toString());
            Main.consolePrint("Player's turn! Press A to attack, D to defend, and P to heal.");
        }

        // Don't do anything if it's the player's turn— await keypress
        int newTicks = this.ticks + 1;
        BattleWorld nbw = new BattleWorld(newTicks, this.prevWorld, this.player, this.mob, this.playerTurn);
        return nbw;
    }


    /**
     * Calculates the damage that should be done.
     * equivalent to a die roll on FieldWorld.ATTACK_LEVEL
     * Can never hit 0, just in case 0 triggers a divide by zero error.
     *
     * @return
     */
    public int damageAmount() {
        // Add 1 so that there is never a divide by zero issue caused by defense calcuation
        int dmg = Math.abs(Main.RAND.nextInt(FieldWorld.ATTACK_LEVEL)) + 1;

        Main.consolePrint("Damage dealt=" + dmg);
        return dmg;
    }

    /**
     * Returns a BattleWorld reflecting what action the Mob took
     *
     * @return BattleWorld
     */
    public World actionMob() {
        Main.consolePrint("Mob is deciding...");
        int choice = Math.abs(Main.RAND.nextInt(2));
        // 0. Attack player
        if (choice == 0) {
            Main.consolePrint("Mob attacks player");
            Player newPlayer = this.player.removeHP(damageAmount());
            return new BattleWorld(0, this.prevWorld, newPlayer, this.mob, true);
        }
        // 1. Put Mob into defense mode
        else {
            Mob newMob = this.mob.activateDefend();
            Main.consolePrint("Mob activated defense mode!");
            return new BattleWorld(0, this.prevWorld, this.player, newMob, true);
        }
    }


    /**
     * Event handler for keypresses. Only returns when playerTurn is true.
     * returns appropriate BattleWorld state based on desired player action
     *
     * @param s Keypress
     * @return BattleWorld
     */
    @Override
    public World onKeyEvent(String s) {

        // Only accept key input when it is the player's turn
        if (this.playerTurn) {
            // Player ATTACKs
            if (s.equalsIgnoreCase("A")) {
                Main.consolePrint("Player attacks!");
                Mob nm = this.mob.removeHP(damageAmount());
                BattleWorld nbw = new BattleWorld(0, this.prevWorld, this.player, nm, false);
                return nbw;
            }

            // Player DEFENDS
            if (s.equalsIgnoreCase("D")) {
                Main.consolePrint("Player defends.");
                Player np = this.player.activateDefend();
                BattleWorld nbwDef = new BattleWorld(0, this.prevWorld, np, this.mob, false);
                return nbwDef;
            }

            if (s.equalsIgnoreCase("P")) {
                Main.consolePrint("Player heals.");
                Player npHeal = this.player.addHP(FieldWorld.HEAL_AMOUNT);
                BattleWorld nbwHeal = new BattleWorld(0, this.prevWorld, npHeal, this.mob, false);
                return nbwHeal;
            }
        }
        return this;
    }

    /**
     * String rep of BattleWorld
     *
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


    /**
     * Checks if two BattleWorlds are the same... can be used for all objects and null!
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        BattleWorld that = (BattleWorld) o;
        return Objects.equals(playerTurn, that.playerTurn) &&
                Objects.equals(prevWorld, that.prevWorld) &&
                Objects.equals(player, that.player) &&
                Objects.equals(mob, that.mob);
    }

    /**
     * Draws the BattleWorld
     *
     * @return WorldImage
     */
    public WorldImage makeImage() {
        return new CircleImage(new Posn(10, 10), 10, Color.blue);
    }

}
