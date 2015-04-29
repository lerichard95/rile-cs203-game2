package me.lerichard;

import tester.IExamples;
import tester.Tester;

/**
 * This class is used to run tests
 * Created by richard on 4/29/15.
 */
public class Examples implements IExamples {
    Examples() {
    }


    /**
     * Runs the appropriate tests
     *
     * @param t
     */
    public void tests(Tester t) {
        testMobAddHP(t);
    }

    /**
     * Tests the addHP() method for Mob
     */
    public void testMobAddHP(Tester t) {
        int randInt1 = 1 + Main.RAND.nextInt();
        boolean randBool1 = Main.RAND.nextBoolean();
        Mob testMob1 = new Mob(randInt1, randInt1, randInt1, randBool1);
        Mob testExpect1 = new Mob(randInt1 * 2, randInt1, randInt1, randBool1);
        t.checkExpect(testMob1.addHP(randInt1), testExpect1, "Mob addHP");

    }


}
