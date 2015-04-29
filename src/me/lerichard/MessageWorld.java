package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.CircleImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

import java.awt.*;

/**
 * MessageWorld displays a black screen with the appropriate text.
 * Created by richard on 4/15/15.
 */
public class MessageWorld extends World {
    String message;
    World next;

    MessageWorld(String msg, World nxt) {
        this.message = msg;
        this.next = nxt;
    }

    @Override
    public World onTick() {
        Main.consolePrint("Press SPACE to continue");
        return this;
    }

    /**
     * Exit the screen and continue the game
     *
     * @param ke a keypress
     * @return a new World
     */
    public World onKeyEvent(String ke) {
        if (ke.equalsIgnoreCase(" ")) {
            Main.consolePrint("Exited MessageWorld");
            return next;
        }
        return this;
    }

    /**
     * Draw text onto the screen
     *
     * @return WorldImage representing the message passed
     */
    public WorldImage makeImage() {
        return new CircleImage(new Posn(10, 10), 10, Color.blue);
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
