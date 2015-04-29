package me.lerichard;

import java.util.Random;

public class Main {
    public static final int SHOW_MESSAGE_FOR_N_TICKS = 4;
    public static Random RAND = new Random();
    public static boolean consoleMode = true;

    public static void main(String[] args) {
        FieldWorld game = new FieldWorld();
        game.bigBang(500, 500, 4.0);
        System.out.println("Hello world!");
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