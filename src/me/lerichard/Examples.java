package me.lerichard;

import tester.Tester;

/**
 * This class is used to run tests
 * Created by richard on 4/29/15.
 */
public class Examples {
    Examples() {
    }


    /**
     * Runs the appropriate tests
     *
     * @param t
     */
    public void tests(Tester t) {
        testMobAddHP(t);
        testMobRemoveHP(t);
        testMobIsAlive(t);
    }

    /**
     * Tests the addHP() method for Mob
     */
    public void testMobAddHP(Tester t) {
        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
        boolean randBool1 = Main.RAND.nextBoolean();
        Actor testMob1 = new Actor(randInt1, randInt1, randInt1, randBool1, ActorType.MOB);
        Actor testExpect1 = new Actor(randInt1 * 2, randInt1, randInt1, false, ActorType.MOB);
        t.checkExpect(testMob1.addHP(randInt1), testExpect1, "Mob addHP()");
    }

    /**
     * Test method for Mob removeHP()
     *
     * @param t
     */
    public void testMobRemoveHP(Tester t) {
        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
        int randInt2 = 1 + Main.RAND.nextInt(randInt1);
        int expectInt = randInt1 - randInt2;
        //Defense OFF
        Actor testMob1 = new Actor(randInt1, randInt1, randInt1, false, ActorType.MOB);
        Actor testExpect1 = new Actor(expectInt, randInt1, randInt1, false, ActorType.MOB);
        t.checkExpect(testMob1.removeHP(randInt2), testExpect1, "Mob removeHP()");

        //Defense ON - Checking for divide by zero caused by casting
        int expectDamageDef = randInt2;
        if (randInt2 >= FieldWorld.DEFENSE_LEVEL) {
            expectDamageDef = (int) (randInt2 / FieldWorld.DEFENSE_LEVEL);
        }
        int expectHPDef = randInt1 - expectDamageDef;
        Actor testMob2 = new Actor(randInt1, randInt1, randInt1, true, ActorType.MOB);
        Actor testExpect2 = new Actor(expectHPDef, randInt1, randInt1, false, ActorType.MOB);
        t.checkExpect(testMob2.removeHP(randInt2), testExpect2, "Mob removeHP() - defense mode");

        int randIntZero = Math.abs(Main.RAND.nextInt(FieldWorld.DEFENSE_LEVEL));
        int expectDamageDef2 = randIntZero;
        if (randInt2 >= FieldWorld.DEFENSE_LEVEL) {
            expectDamageDef = (int) (randIntZero / FieldWorld.DEFENSE_LEVEL);
        }
        int expectHPDef2 = randInt1 - expectDamageDef2;
        Actor testMob3 = new Actor(randInt1, randInt1, randInt1, true, ActorType.MOB);
        Actor testExpect3 = new Actor(expectHPDef2, randInt1, randInt1, false, ActorType.MOB);
        t.checkExpect(testMob3.removeHP(randIntZero), testExpect3,
                "Actor removeHP() - defense mode: divide by zero case");

    }

    /**
     * Test method for Actor isAlive()
     *
     * @param t
     */
    public void testMobIsAlive(Tester t) {
        int randInt1 = Main.RAND.nextInt();
        boolean randBool1 = true;
        if (randInt1 <= 0) {
            randBool1 = false;
        }
        Actor testMob1 = new Actor(randInt1, randInt1, randInt1, randBool1, ActorType.MOB);
        t.checkExpect(testMob1.isAlive(), randBool1, "Mob isAlive()");
    }

    /**
     * Test method for Mob isDef()
     *
     * @param t
     */
    public void testMobIsDef(Tester t) {
        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
        boolean randBool1 = Main.RAND.nextBoolean();
        Actor testMob1 = new Actor(randInt1, randInt1, randInt1, randBool1, ActorType.MOB);
        t.checkExpect(testMob1.isDef(), randBool1, "Mob isDef()");
    }

    /**
     * Tester method for Mob activateDefend()
     *
     * @param t
     */
    public void testMobActivateDefend(Tester t) {
        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
        boolean randBool1 = Main.RAND.nextBoolean();
        boolean expect = true;
        Actor testMob1 = new Actor(randInt1, randInt1, randInt1, randBool1, ActorType.MOB);
        Actor testExpect = new Actor(randInt1, randInt1, randInt1, expect, ActorType.MOB);
        t.checkExpect(testMob1.activateDefend(), testExpect, "Actor activateDefend()");
    }

    /**
     * Test method for Mob equals()
     *
     * @param t
     */
    public void testMobEquals(Tester t) {
        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
        boolean randBool1 = Main.RAND.nextBoolean();
        Actor testMob1 = new Actor(randInt1, randInt1, randInt1, randBool1, ActorType.MOB);
        Actor testExpect = new Actor(randInt1, randInt1, randInt1, randBool1, ActorType.MOB);
        Actor testExpect2 = new Actor(0, 0, 0, true, ActorType.MOB);
        t.checkExpect(testMob1.equals(testExpect), true, "Actor equals()");
        t.checkExpect(testMob1.equals(testExpect2), false, "Actor equals() - expect false");
        t.checkExpect(testMob1.equals(null), false, "Actor equals() - null input");
    }


    //********************
//
//
//    /**
//     * Tests the addHP() method for Player
//     */
//    public void testPlayerAddHP(Tester t) {
//        int randInt1 = 1 + Math.abs(Main.RAND.nextInt(FieldWorld.DEFAULT_HIT_POINTS_MAX));
//        int healInt = 1 + Math.abs(Main.RAND.nextInt(FieldWorld.DEFAULT_HIT_POINTS_MAX));
//        int expectHP = randInt1 + healInt;
//        if (expectHP >= FieldWorld.DEFAULT_HIT_POINTS_MAX) {
//            expectHP = FieldWorld.DEFAULT_HIT_POINTS_MAX;
//        }
//        boolean randBool1 = Main.RAND.nextBoolean();
//        Player testPlayer1 = new Player(randInt1, randInt1, randInt1, randBool1);
//        Player testExpect1 = new Player(expectHP, randInt1, randInt1 - 1, false);
//        t.checkExpect(testPlayer1.addHP(healInt), testExpect1, "Player addHP()");
//    }
//
//    /**
//     * Test method for Player removeHP()
//     *
//     * @param t
//     */
//    public void testPlayerRemoveHP(Tester t) {
//        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
//        int randInt2 = 1 + Main.RAND.nextInt(randInt1);
//        int expectInt = randInt1 - randInt2;
//        //Defense OFF
//        Player testPlayer1 = new Player(randInt1, randInt1, randInt1, false);
//        Player testExpect1 = new Player(expectInt, randInt1, randInt1, false);
//        t.checkExpect(testPlayer1.removeHP(randInt2), testExpect1, "Player removeHP()");
//
//        //Defense ON - Checking for divide by zero caused by casting
//        int expectDamageDef = randInt2;
//        if (randInt2 >= FieldWorld.DEFENSE_LEVEL) {
//            expectDamageDef = (int) (randInt2 / FieldWorld.DEFENSE_LEVEL);
//        }
//        int expectHPDef = randInt1 - expectDamageDef;
//        Player testPlayer2 = new Player(randInt1, randInt1, randInt1, true);
//        Player testExpect2 = new Player(expectHPDef, randInt1, randInt1, false);
//        t.checkExpect(testPlayer2.removeHP(randInt2), testExpect2, "Player removeHP() - defense mode");
//
//        int randIntZero = Math.abs(Main.RAND.nextInt(FieldWorld.DEFENSE_LEVEL));
//        int expectDamageDef2 = randIntZero;
//        if (randInt2 >= FieldWorld.DEFENSE_LEVEL) {
//            expectDamageDef = (int) (randIntZero / FieldWorld.DEFENSE_LEVEL);
//        }
//        int expectHPDef2 = randInt1 - expectDamageDef2;
//        Player testPlayer3 = new Player(randInt1, randInt1, randInt1, true);
//        Player testExpect3 = new Player(expectHPDef2, randInt1, randInt1, false);
//        t.checkExpect(testPlayer3.removeHP(randIntZero), testExpect3,
//                "Player removeHP() - defense mode: divide by zero case");
//
//
//    }
//
//    /**
//     * Test method for Player isAlive()
//     *
//     * @param t
//     */
//    public void testPlayerIsAlive(Tester t) {
//        int randInt1 = Main.RAND.nextInt();
//        boolean randBool1 = true;
//        if (randInt1 <= 0) {
//            randBool1 = false;
//        }
//        Player testPlayer1 = new Player(randInt1, randInt1, randInt1, randBool1);
//        t.checkExpect(testPlayer1.isAlive(), randBool1, "Player isAlive()");
//    }
//
//    /**
//     * Test method for Player isDef()
//     *
//     * @param t
//     */
//    public void testPlayerIsDef(Tester t) {
//        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
//        boolean randBool1 = Main.RAND.nextBoolean();
//        Player testPlayer1 = new Player(randInt1, randInt1, randInt1, randBool1);
//        t.checkExpect(testPlayer1.isDef(), randBool1, "Player isDef()");
//    }
//
//    /**
//     * Tester method for Player activateDefend()
//     *
//     * @param t
//     */
//    public void testPlayerActivateDefend(Tester t) {
//        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
//        boolean randBool1 = Main.RAND.nextBoolean();
//        boolean expect = true;
//        Player testPlayer1 = new Player(randInt1, randInt1, randInt1, randBool1);
//        Player testExpect = new Player(randInt1, randInt1, randInt1, expect);
//        t.checkExpect(testPlayer1.activateDefend(), testExpect, "Player activateDefend()");
//    }
//
//    /**
//     * Test method for Player equals()
//     *
//     * @param t
//     */
//    public void testPlayerEquals(Tester t) {
//        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
//        boolean randBool1 = Main.RAND.nextBoolean();
//        Player testPlayer1 = new Player(randInt1, randInt1, randInt1, randBool1);
//        Player testExpect = new Player(randInt1, randInt1, randInt1, randBool1);
//        Player testExpect2 = new Player(0, 0, 0, true);
//        t.checkExpect(testPlayer1.equals(testExpect), true, "Player equals()");
//        t.checkExpect(testPlayer1.equals(testExpect2), false, "Player equals() - expect false");
//        t.checkExpect(testPlayer1.equals(null), false, "Player equals() - null input");
//    }
//
//    /**
//     * Test for BattleWorld key events
//     *
//     * @param t
//     */
//    public void testBattleKeyEvent(Tester t) {
//        boolean testBool1 = true;
//        int randInt1 = 1 + Main.RAND.nextInt(99);
//        FieldWorld prevWorld = new FieldWorld();
//        Player testPlayer1 = new Player(randInt1, randInt1, randInt1, testBool1);
//        Mob testMob1 = new Mob(randInt1, randInt1, testBool1);
//        World testBw1 = new BattleWorld(randInt1, prevWorld, testPlayer1, testMob1, testBool1);
//
//        // Not possible to test the exact output of testBw1 because we can't examine specific fields of
//        // a superclass
//        // Attack
//
//        int expectHP1 = 0;
//        testBw1 = testBw1.onKeyEvent("A");
//        Mob expectMob1 = new Mob(expectHP1, randInt1, false);
//        BattleWorld expectBw1 = new BattleWorld(0, prevWorld, testPlayer1, expectMob1, false);
//
//        //(expectMob1.hitPoints < testMob1.hitPoints);
//        t.checkExpect((testBw1.onKeyEvent("A") instanceof BattleWorld), true, "onKeyEvent() - SPACEBAR");
//
//        // Defend
//        t.checkExpect((testBw1.onKeyEvent("D") instanceof BattleWorld), true, "onKeyEvent() - DEFEND");
//
//        // Heal
//        t.checkExpect((testBw1.onKeyEvent("H") instanceof BattleWorld), true, "onKeyEvent() - DEFEND");
//
//    }
//
//    public void testBattleDamageAmount(Tester t) {
//        t.checkExpect(
//                (BattleWorld.damageAmount() < FieldWorld.ATTACK_LEVEL), true, "BattleWorld.damageAmount()");
//    }
//


}
