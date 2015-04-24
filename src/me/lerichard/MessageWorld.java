package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.WorldImage;

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

    /**
     * Exit the screen and continue the game
     *
     * @param ke a keypress
     * @return a new World
     */
    public World onKeyEvent(String ke) {
        if (ke.equals("Space")) {
            return next;
        }
        return this;
    }

    /**
     * Draw text onto the screen
     * @return WorldImage representing the message passed
     */
    public WorldImage makeImage() {
        return null;
    }
}
