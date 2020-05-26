package io.github.joeypekar.scorekeeper.db;

import io.github.joeypekar.scorekeeper.Scoreboard;

public class TestBoard extends Scoreboard {

    private int integerTest = 0;
    private double doubleTest = 0;

    public int getIntegerTest() {
        return integerTest;
    }

    public void setIntegerTest(int integerTest) {
        this.integerTest = integerTest;
    }

    public double getDoubleTest() {
        return doubleTest;
    }

    public void setDoubleTest(double doubleTest) {
        this.doubleTest = doubleTest;
    }

    public void incrementIntTest() {

        integerTest += 1;

    }

    public void incrementIntTest(int by) {

        integerTest += by;

    }

    public void incrementDoubleTest() {

        doubleTest += 1;

    }

    public void incrementDoubleTest(double by) {

        doubleTest += by;

    }
}
