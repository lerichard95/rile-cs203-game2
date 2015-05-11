package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.*;

import java.awt.*;
import java.util.Objects;

/**
 * Created by richard on 4/16/15.
 */
public class BattleWorld extends World {
    static int waitTime;
    static int FONT_SIZE = 14;
    static String LABEL_PLAYER = "PLAYER!";

    FieldWorld prevWorld;
    Actor player;
    Actor mob;
    boolean playerTurn;
    int ticks;


    public BattleWorld(int ticks, FieldWorld prevWorld, Actor player, Actor mob, boolean playerTurn) {
        this.ticks = ticks;
        this.prevWorld = prevWorld;
        this.player = player;
        this.mob = mob;
        this.playerTurn = playerTurn;
    }

    /**
     * Calculates the damage that should be done.
     * equivalent to a die roll on FieldWorld.ATTACK_LEVEL
     * Can never hit 0, just in case 0 triggers a divide by zero error.
     *
     * @return
     */
    public static int damageAmount(int atk) {

        // Add 1 so that there is never a divide by zero issue caused by defense calcuation
        int dmg = Math.abs(1 + Main.RAND.nextInt(atk));
        // Dealing at least 90 percent of the max damage earns a critical hit!
        if (dmg >= (int) (atk * .90)) {
            // Deal the full damage
            Main.consolePrint("CRITICAL HIT! Rolled a " + dmg);
            dmg = atk;
        }

        Main.consolePrint("Damage =" + dmg);
        return dmg;
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
                Actor newPlayerState =
                        new Actor(this.player.hitPoints,
                                this.player.atkLevel, this.player.defPower, false, this.player.type, newPots);
                newFieldWorld =
                        new FieldWorld(0,
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
            Actor newPlayer = this.player.removeHP(damageAmount(this.mob.atkLevel));
            return new BattleWorld(0, this.prevWorld, newPlayer, this.mob, true);
        }
        // 1. Put Mob into defense mode
        else {
            Actor newMob = this.mob.activateDefend();
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
                Actor nm = this.mob.removeHP(damageAmount(this.player.atkLevel));
                BattleWorld nbw = new BattleWorld(0, this.prevWorld, this.player, nm, false);
                return nbw;
            }

            // Player DEFENDS
            if (s.equalsIgnoreCase("D")) {
                Main.consolePrint("Player defends.");
                Actor np = this.player.activateDefend();
                BattleWorld nbwDef = new BattleWorld(0, this.prevWorld, np, this.mob, false);
                return nbwDef;
            }

            if (s.equalsIgnoreCase("P")) {
                Main.consolePrint("Player heals.");
                Actor npHeal = this.player.addHP(FieldWorld.HEAL_AMOUNT);
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
        return new OverlayImages(playerStats(), this.mob.draw());
        //return new CircleImage(new Posn(10, 10), 10, Color.blue);
    }

    public WorldImage playerStats() {

        // height of a line is 14
        int lineNum = 0;
        Posn bgBoxCoord = new Coord(FieldWorld.FIELD_OBJECT_RADIUS + 150, FieldWorld.FIELD_OBJECT_RADIUS * 17);
        Posn playerCoord = new Posn(bgBoxCoord.x - 100, bgBoxCoord.y - 14);


        WorldImage playerLabel = new TextImage(
                playerCoord,
                BattleWorld.LABEL_PLAYER,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage playerHP = new TextImage(
                new Posn(playerCoord.x, playerCoord.y + 14),
                "HP: " + this.player.hitPoints,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage playerPots = new TextImage(
                new Posn(playerCoord.x, playerCoord.y + 14 * 2),
                "Potions: " + this.player.hpPots,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        RectangleImage bgBox = new RectangleImage(
                bgBoxCoord,
                12 * FieldWorld.FIELD_OBJECT_RADIUS,
                4 * 14,
                Color.BLACK);

        WorldImage player2 = new OverlayImages(playerHP, playerPots);
        WorldImage player1 = new OverlayImages(playerLabel, player2);
        WorldImage stats = new OverlayImages(bgBox, player1);
        return stats;
    }

    /*public WorldImage mobStats() {

    }*/

}
