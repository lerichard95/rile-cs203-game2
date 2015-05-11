package me.lerichard;

import javalib.funworld.World;
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
        testActorAddHP(t);
        testActorRemoveHP(t);
        testActorIsAlive(t);
    }

    /**
     * Tests the addHP() method for Actor
     */
    public void testActorAddHP(Tester t) {
        int randTypeIndex = Math.abs(Main.RAND.nextInt(ActorType.values().length));
        ActorType randType = ActorType.values()[randTypeIndex];
        int randInt1 = 1 + Math.abs(Main.RAND.nextInt(FieldWorld.DEFAULT_HIT_POINTS_MAX));
        int healInt = 1 + Math.abs(Main.RAND.nextInt(FieldWorld.DEFAULT_HIT_POINTS_MAX));
        int expectHP = randInt1 + healInt;
        if (expectHP >= FieldWorld.DEFAULT_HIT_POINTS_MAX) {
            expectHP = FieldWorld.DEFAULT_HIT_POINTS_MAX;
        }
        boolean randBool1 = Main.RAND.nextBoolean();
        Actor testPlayer1 = new Actor(randInt1, randInt1, randInt1, randBool1, randType, randInt1);
        Actor testExpect1 = new Actor(expectHP, randInt1, randInt1, false, randType, randInt1 - 1);
        t.checkExpect(testPlayer1.addHP(healInt), testExpect1, "Player addHP()");
    }

    /**
     * Test method for Actor removeHP()
     *
     * @param t
     */
    public void testActorRemoveHP(Tester t) {
        int randTypeIndex = Math.abs(Main.RAND.nextInt(ActorType.values().length));
        ActorType randType = ActorType.values()[randTypeIndex];

        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
        int randInt2 = 1 + Main.RAND.nextInt(randInt1);
        int expectInt = randInt1 - randInt2;
        int testDefPower = FieldWorld.DEFENSE_LEVEL;
        //Defense OFF
        Actor testMob1 = new Actor(randInt1, randInt1, testDefPower, false, randType, randInt1);
        Actor testExpect1 = new Actor(expectInt, randInt1, testDefPower, false, randType, randInt1);
        t.checkExpect(testMob1.removeHP(randInt2), testExpect1, "Mob removeHP()");

        //Defense ON - Checking for divide by zero caused by casting
        int expectDamageDef = randInt2;
        if (randInt2 >= testDefPower) {
            expectDamageDef = (int) (randInt2 / testDefPower);
        }
        int expectHPDef = randInt1 - expectDamageDef;
        Actor testMob2 = new Actor(randInt1, randInt1, testDefPower, true, randType, randInt1);
        Actor testExpect2 = new Actor(expectHPDef, randInt1, testDefPower, false, randType, randInt1);
        t.checkExpect(testMob2.removeHP(randInt2), testExpect2, "Mob removeHP() - defense mode");

        int randIntZero = Math.abs(Main.RAND.nextInt(FieldWorld.DEFENSE_LEVEL));
        int expectDamageDef2 = randIntZero;
        if (randInt2 >= FieldWorld.DEFENSE_LEVEL) {
            expectDamageDef = (int) (randIntZero / FieldWorld.DEFENSE_LEVEL);
        }
        int expectHPDef2 = randInt1 - expectDamageDef2;
        Actor testMob3 = new Actor(randInt1, randInt1, testDefPower, true, randType, randInt1);
        Actor testExpect3 = new Actor(expectHPDef2, randInt1, testDefPower, false, randType, randInt1);
        t.checkExpect(testMob3.removeHP(randIntZero), testExpect3,
                "Actor removeHP() - defense mode: divide by zero case");

    }

    /**
     * Test method for Actor isAlive()
     *
     * @param t
     */
    public void testActorIsAlive(Tester t) {
        int randTypeIndex = Math.abs(Main.RAND.nextInt(ActorType.values().length));
        ActorType randType = ActorType.values()[randTypeIndex];

        int randInt1 = Main.RAND.nextInt();
        boolean randBool1 = true;
        if (randInt1 <= 0) {
            randBool1 = false;
        }
        Actor testMob1 = new Actor(randInt1, randInt1, randInt1, randBool1, randType, randInt1);
        t.checkExpect(testMob1.isAlive(), randBool1, "Mob isAlive()");
    }

    /**
     * Test method for Mob isDef()
     *
     * @param t
     */
    public void testActorIsDef(Tester t) {
        int randTypeIndex = Math.abs(Main.RAND.nextInt(ActorType.values().length));
        ActorType randType = ActorType.values()[randTypeIndex];

        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
        boolean randBool1 = Main.RAND.nextBoolean();
        Actor testMob1 = new Actor(randInt1, randInt1, randInt1, randBool1, randType, randInt1);
        t.checkExpect(testMob1.isDef(), randBool1, "Mob isDef()");
    }

    /**
     * Tester method for Actor activateDefend()
     *
     * @param t
     */
    public void testMobActivateDefend(Tester t) {

        int randTypeIndex = Math.abs(Main.RAND.nextInt(ActorType.values().length));
        ActorType randType = ActorType.values()[randTypeIndex];
        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
        boolean randBool1 = Main.RAND.nextBoolean();
        boolean expect = true;
        Actor testMob1 = new Actor(randInt1, randInt1, randInt1, randBool1, randType, randInt1);
        Actor testExpect = new Actor(randInt1, randInt1, randInt1, expect, randType, randInt1);
        t.checkExpect(testMob1.activateDefend(), testExpect, "Actor activateDefend()");
    }

    /**
     * Test method for Actor equals()
     *
     * @param t
     */
    public void testActorEquals(Tester t) {
        int randTypeIndex = Math.abs(Main.RAND.nextInt(ActorType.values().length));
        ActorType randType = ActorType.values()[randTypeIndex];

        int randInt1 = 1 + Math.abs(Main.RAND.nextInt());
        boolean randBool1 = Main.RAND.nextBoolean();
        Actor testMob1 = new Actor(randInt1, randInt1, randInt1, randBool1, randType, randInt1);
        Actor testExpect = new Actor(randInt1, randInt1, randInt1, randBool1, randType, randInt1);
        Actor testExpect2 = new Actor(0, 0, randInt1, true, ActorType.MOB, 0);
        t.checkExpect(testMob1.equals(testExpect), true, "Actor equals()");
        t.checkExpect(testMob1.equals(testExpect2), false, "Actor equals() - expect false");
        t.checkExpect(testMob1.equals(null), false, "Actor equals() - null input");
    }

    /**
     * Test for BattleWorld key events
     *
     * @param t
     */
    public void testBattleKeyEvent(Tester t) {
        int randTypeIndex = Math.abs(Main.RAND.nextInt(ActorType.values().length));
        ActorType randType = ActorType.values()[randTypeIndex];

        boolean testBool1 = true;
        int randInt1 = 1 + Main.RAND.nextInt(99);

        FieldWorld prevWorld = new FieldWorld();
        Actor testPlayer1 = new Actor(randInt1, randInt1, randInt1, testBool1, randType, randInt1);
        Actor testMob1 = new Actor(randInt1, randInt1, randInt1, testBool1, randType, randInt1);
        World testBw1 = new BattleWorld(randInt1, prevWorld, testPlayer1, testMob1, testBool1);

        // Not possible to test the exact output of testBw1 because we can't examine specific fields of
        // a superclass World

        // Attack
        int expectHP1 = 0;
        testBw1 = testBw1.onKeyEvent("A");
        Actor expectMob1 = new Actor(randInt1, randInt1, randInt1, testBool1, randType, randInt1);
        BattleWorld expectBw1 = new BattleWorld(0, prevWorld, testPlayer1, expectMob1, false);

        //(expectMob1.hitPoints < testMob1.hitPoints);
        t.checkExpect((testBw1.onKeyEvent("A") instanceof BattleWorld), true, "onKeyEvent() - SPACEBAR");

        // Defend
        t.checkExpect((testBw1.onKeyEvent("D") instanceof BattleWorld), true, "onKeyEvent() - DEFEND");

        // Heal
        t.checkExpect((testBw1.onKeyEvent("H") instanceof BattleWorld), true, "onKeyEvent() - DEFEND");
    }

    public void testBattleDamageAmount(Tester t) {
        int randIntATKLEVEL = Math.abs(Main.RAND.nextInt());
        int randInt1 = Math.abs(Main.RAND.nextInt(randIntATKLEVEL));
        t.checkExpect(
                (BattleWorld.damageAmount(randInt1) <= randIntATKLEVEL),
                true, "BattleWorld.damageAmount()");

    }

    public void testFieldRandomBattles(Tester t) {
        int randInt1 = Math.abs(Main.RAND.nextInt());
        boolean randBool1 = Main.RAND.nextBoolean();
        ActorType randActorType1 = ActorType.values()[
                // Get a random actor type
                Math.abs(Main.RAND.nextInt(ActorType.values().length - 1))
                ];
        FieldObjectType randFoType = FieldObjectType.values()[
                // Get a random FieldObject type
                Math.abs(Main.RAND.nextInt(FieldObjectType.values().length - 1))
                ];
        Actor randActor1 = new Actor(randInt1, randInt1, randInt1, randBool1, randActorType1, randInt1);
        Coord randCoord1 = new Coord(randInt1, randInt1);
        FieldObject randFo1 = new FieldObject(randCoord1, randFoType);

        FieldWorld testFw1 = new FieldWorld(
                randInt1,
                randActor1,
                randBool1,
                randCoord1,
                randFo1,
                randInt1);

        // if steps is zero, return false
        t.checkExpect(
                testFw1.getRandomBattle(0),
                false, "getRandomBattle() - steps=0");

    }


    /**
     * Test for movement function in FieldWorld
     *
     * @param t
     */

    public void testFieldMoveUp(Tester t) {

        for (int i = 0; i < 50; i++) {
            int randInt1 = Math.abs(Main.RAND.nextInt());
            int randIntX1 = Math.abs(Main.RAND.nextInt(FieldWorld.MAX_FIELD_WIDTH));
            int randIntY1 = Math.abs(Main.RAND.nextInt(FieldWorld.MAX_FIELD_HEIGHT));
            boolean randBool1 = Main.RAND.nextBoolean();

            ActorType randActorType1 = ActorType.values()[
                    // Get a random actor type
                    Math.abs(Main.RAND.nextInt(ActorType.values().length - 1))
                    ];

            /* FieldObjectType randFoType = FieldObjectType.values()[
                    // Get a random FieldObject type
                    Math.abs(Main.RAND.nextInt(FieldObjectType.values().length - 1))
                    ];
            */

            Actor randActor1 = new Actor(randInt1, randInt1, randInt1, randBool1, randActorType1, randInt1);

            // Random treasure coord
            Coord randCoord1 = new Coord(randInt1, randInt1);

            // Important: player coord
            Coord playerCoord1 = new Coord(randIntX1, randIntY1);
            Coord expectCoord1 = new Coord(randIntX1, randIntY1);

            int stepsInt1 = randInt1;
            int expectStepsInt1 = randInt1;

            if (randIntY1 > 0) {
                expectCoord1 = new Coord(randIntX1, randIntY1 - 1);
                expectStepsInt1 = stepsInt1 + 1;
            }

            FieldObject playerFo1 = new FieldObject(playerCoord1, FieldObjectType.PLAYER);
            FieldObject expectFo1 = new FieldObject(expectCoord1, FieldObjectType.PLAYER);

            FieldWorld testFw1 = new FieldWorld(
                    randInt1,
                    randActor1,
                    randBool1,
                    randCoord1,
                    playerFo1,
                    stepsInt1);

            //movePlayerUp()
            FieldWorld expectFw1 = new FieldWorld(
                    randInt1,
                    randActor1,
                    randBool1,
                    randCoord1,
                    expectFo1,
                    expectStepsInt1);

            t.checkExpect(
                    testFw1.movePlayerUp(false),
                    expectFw1,
                    "movePlayerUp()"
            );
        }
    }

    public void testFieldMoveDown(Tester t) {

        for (int i = 0; i < 50; i++) {
            int randInt1 = Math.abs(Main.RAND.nextInt());
            int randIntX1 = Math.abs(Main.RAND.nextInt(FieldWorld.MAX_FIELD_WIDTH));
            int randIntY1 = Math.abs(Main.RAND.nextInt(FieldWorld.MAX_FIELD_HEIGHT));
            boolean randBool1 = Main.RAND.nextBoolean();

            ActorType randActorType1 = ActorType.values()[
                    // Get a random actor type
                    Math.abs(Main.RAND.nextInt(ActorType.values().length - 1))
                    ];

            /* FieldObjectType randFoType = FieldObjectType.values()[
                    // Get a random FieldObject type
                    Math.abs(Main.RAND.nextInt(FieldObjectType.values().length - 1))
                    ];
            */

            Actor randActor1 = new Actor(randInt1, randInt1, randInt1, randBool1, randActorType1, randInt1);

            // Random treasure coord
            Coord randCoord1 = new Coord(randInt1, randInt1);

            // Important: player coord
            Coord playerCoord1 = new Coord(randIntX1, randIntY1);
            Coord expectCoord1 = new Coord(randIntX1, randIntY1);

            int stepsInt1 = randInt1;
            int expectStepsInt1 = randInt1;

            if (randIntY1 < FieldWorld.MAX_FIELD_HEIGHT) {
                expectCoord1 = new Coord(randIntX1, randIntY1 + 1);
                expectStepsInt1 = stepsInt1 + 1;
            }

            FieldObject playerFo1 = new FieldObject(playerCoord1, FieldObjectType.PLAYER);
            FieldObject expectFo1 = new FieldObject(expectCoord1, FieldObjectType.PLAYER);

            FieldWorld testFw1 = new FieldWorld(
                    randInt1,
                    randActor1,
                    randBool1,
                    randCoord1,
                    playerFo1,
                    stepsInt1);

            //movePlayerUp()
            FieldWorld expectFw1 = new FieldWorld(
                    randInt1,
                    randActor1,
                    randBool1,
                    randCoord1,
                    expectFo1,
                    expectStepsInt1);

            t.checkExpect(
                    testFw1.movePlayerDown(false),
                    expectFw1,
                    "movePlayerDown()"
            );
        }
    }

    public void testFieldMoveLeft(Tester t) {

        for (int i = 0; i < 50; i++) {
            int randInt1 = Math.abs(Main.RAND.nextInt());
            int randIntX1 = Math.abs(Main.RAND.nextInt(FieldWorld.MAX_FIELD_WIDTH));
            int randIntY1 = Math.abs(Main.RAND.nextInt(FieldWorld.MAX_FIELD_HEIGHT));
            boolean randBool1 = Main.RAND.nextBoolean();

            ActorType randActorType1 = ActorType.values()[
                    // Get a random actor type
                    Math.abs(Main.RAND.nextInt(ActorType.values().length - 1))
                    ];

            /* FieldObjectType randFoType = FieldObjectType.values()[
                    // Get a random FieldObject type
                    Math.abs(Main.RAND.nextInt(FieldObjectType.values().length - 1))
                    ];
            */

            Actor randActor1 = new Actor(randInt1, randInt1, randInt1, randBool1, randActorType1, randInt1);

            // Random treasure coord
            Coord randCoord1 = new Coord(randInt1, randInt1);

            // Important: player coord
            Coord playerCoord1 = new Coord(randIntX1, randIntY1);
            Coord expectCoord1 = new Coord(randIntX1, randIntY1);

            int stepsInt1 = randInt1;
            int expectStepsInt1 = randInt1;

            if (randIntX1 > 0) {
                expectCoord1 = new Coord(randIntX1 - 1, randIntY1);
                expectStepsInt1 = stepsInt1 + 1;
            }

            FieldObject playerFo1 = new FieldObject(playerCoord1, FieldObjectType.PLAYER);
            FieldObject expectFo1 = new FieldObject(expectCoord1, FieldObjectType.PLAYER);

            FieldWorld testFw1 = new FieldWorld(
                    randInt1,
                    randActor1,
                    randBool1,
                    randCoord1,
                    playerFo1,
                    stepsInt1);

            //movePlayerUp()
            FieldWorld expectFw1 = new FieldWorld(
                    randInt1,
                    randActor1,
                    randBool1,
                    randCoord1,
                    expectFo1,
                    expectStepsInt1);

            t.checkExpect(
                    testFw1.movePlayerLeft(false),
                    expectFw1,
                    "movePlayerLeft()"
            );
        }
    }

    public void testFieldMoveRight(Tester t) {

        for (int i = 0; i < 50; i++) {
            int randInt1 = Math.abs(Main.RAND.nextInt());
            int randIntX1 = Math.abs(Main.RAND.nextInt(FieldWorld.MAX_FIELD_WIDTH));
            int randIntY1 = Math.abs(Main.RAND.nextInt(FieldWorld.MAX_FIELD_HEIGHT));
            boolean randBool1 = Main.RAND.nextBoolean();

            ActorType randActorType1 = ActorType.values()[
                    // Get a random actor type
                    Math.abs(Main.RAND.nextInt(ActorType.values().length - 1))
                    ];

            /* FieldObjectType randFoType = FieldObjectType.values()[
                    // Get a random FieldObject type
                    Math.abs(Main.RAND.nextInt(FieldObjectType.values().length - 1))
                    ];
            */

            Actor randActor1 = new Actor(randInt1, randInt1, randInt1, randBool1, randActorType1, randInt1);

            // Random treasure coord
            Coord randCoord1 = new Coord(randInt1, randInt1);

            // Important: player coord
            Coord playerCoord1 = new Coord(randIntX1, randIntY1);
            Coord expectCoord1 = new Coord(randIntX1, randIntY1);

            int stepsInt1 = randInt1;
            int expectStepsInt1 = randInt1;

            if (randIntX1 < FieldWorld.MAX_FIELD_WIDTH) {
                expectCoord1 = new Coord(randIntX1 + 1, randIntY1);
                expectStepsInt1 = stepsInt1 + 1;
            }

            FieldObject playerFo1 = new FieldObject(playerCoord1, FieldObjectType.PLAYER);
            FieldObject expectFo1 = new FieldObject(expectCoord1, FieldObjectType.PLAYER);

            FieldWorld testFw1 = new FieldWorld(
                    randInt1,
                    randActor1,
                    randBool1,
                    randCoord1,
                    playerFo1,
                    stepsInt1);

            //movePlayerRight()
            FieldWorld expectFw1 = new FieldWorld(
                    randInt1,
                    randActor1,
                    randBool1,
                    randCoord1,
                    expectFo1,
                    expectStepsInt1);

            t.checkExpect(
                    testFw1.movePlayerRight(false),
                    expectFw1,
                    "movePlayerRight()"
            );
        }
    }

}
