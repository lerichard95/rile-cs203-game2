package me.lerichard;

import java.util.Random;

public class Main {
    public static Random RAND = new Random();
    public static boolean consoleMode = true;

    public static void main(String[] args) {
        //FieldWorld game = new FieldWorld().bigBang();
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
