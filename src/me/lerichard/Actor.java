package me.lerichard;

/**
 * Actors are characters in the game
 */
public interface Actor {

    /**
     * Adds HP to the Actor
     *
     * @return a new Actor with added HP
     */
    Actor addHP(int p);

    /**
     * Removes HP from the Actor
     *
     * @return a new Actor with removed HP
     */
    Actor removeHP(int p);

    /**
     * Returns a
     *
     * @return a dead Actor
     */
    Actor kill();

    /**
     * Returns true if the Actor is alive or dead
     *
     * @return true if Actor is alive
     */
    boolean isAlive();

}
