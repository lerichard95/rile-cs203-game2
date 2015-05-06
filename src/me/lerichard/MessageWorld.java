package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.*;

import java.awt.*;

/**
 * MessageWorld displays a black screen with the appropriate text.
 * Created by richard on 4/15/15.
 */
public class MessageWorld extends World {

    public int ticks;

    String message;
    World next;

    MessageWorld(int ticks, String msg, World nxt) {
        this.ticks = ticks;
        this.message = msg;
        this.next = nxt;
    }

    @Override
    public World onTick() {
        if (this.ticks < Main.SHOW_MESSAGE_FOR_N_TICKS) {

            if (Main.consoleMode) {
                Main.consolePrint("====== Message =======");
                Main.consolePrint(this.message);
            }

            int newTicks = this.ticks + 1;
            MessageWorld newMsgWorld =
                    new MessageWorld(newTicks, this.message, this.next);
            return newMsgWorld;
        }

        if (this.ticks > 2) {
            return this.next;
        }

        // Update the ticks after a tick has occurred
        int newTicks = this.ticks + 1;
        MessageWorld newMsgWorld =
                new MessageWorld(newTicks, this.message, this.next);
        return newMsgWorld;


    }

    /**
     * Exit the screen and continue the game
     *
     * @param ke a keypress
     * @return a new World
     */
    public World onKeyEvent(String ke) {
        /*
        if (ke.equalsIgnoreCase(" ")) {
            if (Main.consoleMode) {
                Main.consolePrint("Exiting MessageWorld...");
            }
            return next;
        }
        */
        return this;

    }

    /**
     * Draw text onto the screen
     *
     * @return WorldImage representing the message passed
     */
    public WorldImage makeImage() {
        Posn windowPinhole = new Posn(Main.WINDOW_WIDTH / 2, Main.WINDOW_WIDTH / 2);
        WorldImage background = new RectangleImage(windowPinhole, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, Color.BLACK);
        WorldImage message = new TextImage(windowPinhole, this.message, 24, 0, Color.WHITE);
        return new OverlayImages(background, message);


    }

    /**
     * String rep of MessageWorld
     *
     * @return
     */
    @Override
    public String toString() {
        return "MessageWorld{" +
                "message='" + message + '\'' +
                ", next=" + next +
                '}';
    }

    /**
     * Determines if an object and MessageWorld represent the same
     *
     * @param o object to compare
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageWorld that = (MessageWorld) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return !(next != null ? !next.equals(that.next) : that.next != null);

    }


}
