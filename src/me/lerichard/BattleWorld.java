package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.*;

import java.awt.*;
import java.util.Objects;

/**
 * Battle mode state
 * Created by richard on 4/16/15.
 */
public class BattleWorld extends World {
    static int waitTime;
    static int FONT_SIZE = 14;
    static String LABEL_PLAYER = "Player";

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
     * @return returns a random int of damage to be dealt
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
     * Controls main BattleWorld game logic. Exits to field and rewards player if victory. Exits to MessageWorld and a new FieldWorld if player dies.
     *
     * @return World
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
     * @return BattleWorld with new player state
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
     * @return BattleWorld with updated state
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

        Posn statLocPlayer = new Coord(
                FieldWorld.FIELD_OBJECT_RADIUS + 60,
                FieldWorld.FIELD_OBJECT_RADIUS * 17);
        Posn statLocMob = new Posn(
                FieldWorld.MAX_FIELD_WIDTH * FieldWorld.FIELD_OBJECT_RADIUS + 150,
                FieldWorld.FIELD_OBJECT_RADIUS * 3);


        WorldImage playerStats = actorStats(this.player, statLocPlayer);
        WorldImage mobStats = actorStats(this.mob, statLocMob);
        WorldImage statBoxes = new OverlayImages(playerStats, mobStats);
        WorldImage outImg = new OverlayImages(statBoxes, controlPanel());

        return new OverlayImages(outImg, this.mob.draw());
        //return new CircleImage(new Posn(10, 10), 10, Color.blue);
    }

    /**
     * Draws the panel for displaying player's turn and controls
     * @return
     */
    public WorldImage controlPanel() {
        // height of a line is 14
        int lineNum = 0;
        Posn loc = new Posn(350, 420);
        Posn actorCoord = new Posn(loc.x - 0, loc.y - 20);

        WorldImage line1 = new TextImage(
                actorCoord,
                BattleWorld.LABEL_PLAYER + "'s turn! Press...",
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage line2 = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14),
                "[ A ] to attack",
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage line3 = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14 * 2),
                "[ D ] to defend",
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage line4 = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14 * 3),
                "[ P ] to heal",
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );


        RectangleImage bgBox = new RectangleImage(
                loc,
                7 * FieldWorld.FIELD_OBJECT_RADIUS,
                5 * 14,
                Color.BLACK);

        WorldImage img5 = new OverlayImages(line1, line2);
        WorldImage img4 = new OverlayImages(img5, line3);
        WorldImage img3 = new OverlayImages(img4, line4);
        WorldImage controls = new OverlayImages(bgBox, img3);
        if (playerTurn) {
            return controls;
        } else {
            return bgBox;
        }


    }

    /**
     *
     * @param actor Actor state to represent
     * @param loc Location of the panel
     * @param action Which action
     * @return WorldImage
     */
    public WorldImage actorAction(Actor actor, Posn loc, String action) {
        Posn actorCoord = new Posn(loc.x - 0, loc.y - 20);

        WorldImage line1 = new TextImage(
                actorCoord,
                " attacked!",
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage line2 = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14),
                "[ A ] to attack",
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage line3 = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14 * 2),
                "[ D ] to defend",
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage line4 = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14 * 3),
                "[ P ] to heal",
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );


        RectangleImage bgBox = new RectangleImage(
                loc,
                7 * FieldWorld.FIELD_OBJECT_RADIUS,
                5 * 14,
                Color.BLACK);

        WorldImage img5 = new OverlayImages(line1, line2);
        WorldImage img4 = new OverlayImages(img5, line3);
        WorldImage img3 = new OverlayImages(img4, line4);
        WorldImage controls = new OverlayImages(bgBox, img3);
        if (playerTurn) {
            return controls;
        } else {
            return bgBox;
        }
    }

    /**
     * Draws stat panel for Actor
     * @param actor Actor to represent
     * @param loc Pixel location of panel
     * @return WorldImage
     */
    public WorldImage actorStats(Actor actor, Posn loc) {
        // height of a line is 14
        int lineNum = 0;

        Posn actorCoord = new Posn(loc.x - 0, loc.y - 2 * 14 - 7);

        String actorLabelName;
        if (actor.type.equals(ActorType.PLAYER)) {
            actorLabelName = BattleWorld.LABEL_PLAYER;
        } else {
            actorLabelName = "Mob";
        }

        WorldImage actorLabel = new TextImage(
                new Coord(actorCoord.x, actorCoord.y - 3),
                actorLabelName,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage actorHP = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14),
                "HP: " + actor.hitPoints,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage actorPots = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14 * 2),
                "Potions: " + actor.hpPots,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage actorATKLevel = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14 * 3),
                "ATK Power: " + actor.atkLevel,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage actorDefLevel = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14 * 4),
                "DEF Level: " + actor.defPower,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        String defString = "";
        if (actor.isDef()) {
            defString = "Defending...";
        } else {
            defString = "";
        }
        WorldImage actorIsDef = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14 * 5),
                defString,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        RectangleImage bgBox = new RectangleImage(
                loc,
                5 * FieldWorld.FIELD_OBJECT_RADIUS,
                4 * 25,
                Color.BLACK);

        WorldImage img5 = new OverlayImages(actorLabel, actorHP);
        WorldImage img4 = new OverlayImages(img5, actorPots);
        WorldImage img3 = new OverlayImages(img4, actorATKLevel);
        WorldImage img2 = new OverlayImages(img3, actorDefLevel);
        WorldImage img1 = new OverlayImages(img2, actorIsDef);
        WorldImage stats = new OverlayImages(bgBox, img1);
        return stats;

    }

    /**
     * Draws panel relaying BattleWorld action info
     *
     * @param mobTurn  flag for if
     * @param action
     * @param reaction
     */
    public WorldImage actionBoard(boolean mobTurn, String action, String reaction) {
        Posn loc = new Posn(250,250);
        Posn actorCoord = new Posn(loc.x - 0, loc.y - 20);
        String mobTurnLabel;
        if (mobTurn) {
            mobTurnLabel = "Mob's turn";
        } else {
            mobTurnLabel = "";
        }
        String actualDam = "";

        WorldImage line1 = new TextImage(
                actorCoord,
                mobTurnLabel,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage line2 = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14),
                action,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage line3 = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14 * 2),
                reaction,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );

        WorldImage line4 = new TextImage(
                new Posn(actorCoord.x, actorCoord.y + 14 * 3),
                actualDam,
                BattleWorld.FONT_SIZE,
                0,
                Color.GREEN
        );


        RectangleImage bgBox = new RectangleImage(
                loc,
                7 * FieldWorld.FIELD_OBJECT_RADIUS,
                5 * 14,
                Color.BLACK);

        WorldImage img5 = new OverlayImages(line1, line2);
        WorldImage img4 = new OverlayImages(img5, line3);
        WorldImage img3 = new OverlayImages(img4, line4);
        WorldImage controls = new OverlayImages(bgBox, img3);
        if (playerTurn) {
            return controls;
        } else {
            return bgBox;
        }

    }
}
