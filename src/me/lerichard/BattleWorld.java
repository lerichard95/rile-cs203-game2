package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.WorldImage;

/**
 * Created by richard on 4/16/15.
 */
public class BattleWorld extends World {
    static int waitTime;
    Player player;
    Mob mob;

    public BattleWorld(Player player, Mob mob) {
        this.player = player;
        this.mob = mob;
    }

    @Override
    public WorldImage makeImage() {
        return null;
    }
}
