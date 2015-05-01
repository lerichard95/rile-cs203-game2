package me.lerichard;

import tester.Tester;

import java.util.Random;

public class Main {
    public static final int SHOW_MESSAGE_FOR_N_TICKS = 1;
    public static Random RAND = new Random();

    // consoleMode enables a game logic testing version of the game
    // that does not rely on graphics.
    public static boolean consoleMode = true;
    public static int WINDOW_HEIGHT = FieldWorld.MAX_FIELD_HEIGHT * FieldWorld.FIELD_OBJECT_DIAMETER;
    public static int WINDOW_WIDTH = FieldWorld.MAX_FIELD_WIDTH * FieldWorld.FIELD_OBJECT_DIAMETER;

    public static void main(String[] args) {
        

        FieldWorld game = new FieldWorld();
        MessageWorld start = new MessageWorld(0, "FINITE FANTASY", game);
        Examples e = new Examples();

        Tester.run(e);


        start.bigBang(WINDOW_HEIGHT, WINDOW_WIDTH, .5);

        if (Main.consoleMode) {
            Main.consolePrint("WELCOME TO");
            Main.consolePrint("==========================");
            Main.consolePrint("              FINITE FANTASY          ");
            Main.consolePrint("==========================");
        }
    }

    /**
     * Print to console only when consoleMode is enabled
     */
    public static void consolePrint(String msg) {
        if (Main.consoleMode) {
            System.out.println(msg);
        }
    }
}