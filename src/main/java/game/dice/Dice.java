package game.dice;

import java.util.Random;

import game.creatures.RealmColor;

abstract public class Dice {
    private static final int MAX_VAL = 6;
    private static final int MIN_VAL = 1;
    //color reset
    private static final String reset = "\u001B[0m";
    // to be changed for Green Dice
    // why static?
    // to be used for exceptions
    int value;
    RealmColor color;
    DiceLocation location = DiceLocation.ACTIVE;
    boolean isUsedByBoost = false;
    // the usedByBoost would be changed when the active player uses arcaneBoost
    // but what happens when the passive player uses the arcane boost too!!
    private static Random rand = new Random();

    public Dice(RealmColor color) {
        this.color = color;
        // roll automatic
    }

    public Dice(RealmColor color, int value) {
        this.color = color;
        this.value = value;
    }

    public void roll() {
        value = rand.nextInt(MAX_VAL - MIN_VAL + 1) + MIN_VAL;
    }

    public int getValue() {
        return value;
    }

    public DiceLocation getDiceLocation() {
        return location;
    }

    public void updateDiceLocation(DiceLocation location) {
        this.location = location;
    }

    public boolean isUsedByBoost() {
        return isUsedByBoost;
    }

    public void useByBoost() {
            isUsedByBoost = true;
    }
    public void resetUseByBoost()
    {
        isUsedByBoost = false;
    }

    public boolean setValue(int value) {
        this.value = value;
        return true;
    }

    public RealmColor getRealm() {
        return color;
    }
    public abstract String getTextColor();

    public String toString() {
        return this.getTextColor()+this.color + " Die with Value: " + this.value+ " ("+location+")"+reset;
    }

}
