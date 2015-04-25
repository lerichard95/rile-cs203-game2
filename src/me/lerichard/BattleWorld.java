package me.lerichard;

import javalib.funworld.World;
import javalib.worldimages.WorldImage;

/**
 * Created by richard on 4/16/15.
 */
public class BattleWorld extends World {
    static int waitTime;
    Actor player;
    Actor mob;

    public BattleWorld(Actor player, Actor mob) {
        this.player = player;
        this.mob = mob;
    }

    /**
     *
     * @return
     */
    public World switchboard(){

    }


    public int damageAmount(){
        return Math.abs(
                Main.RAND.nextInt(FieldWorld.ATTACK_LEVEL));
    }


    public World actionMob(){
        int choice = Math.abs(Main.RAND.nextInt(4));
        if (choice == 0) {
            Actor newPlayer = this.player.removeHP(damageAmount());
            return new BattleWorld(newPlayer, this.mob);
        }
        if (choice == 2) {
            Actor newMob = ;
            return new BattleWorld(this.player, newMob);
        }

    }


    @Override
    public WorldImage makeImage() {
        return null;
    }
}
