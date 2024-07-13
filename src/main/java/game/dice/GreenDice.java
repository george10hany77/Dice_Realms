package game.dice;

import game.creatures.RealmColor;

public class GreenDice extends Dice{
    public GreenDice()
    {
        super(RealmColor.GREEN);
    }
    public GreenDice(int value)
    {
        super(RealmColor.GREEN,value);
    }
    public String getTextColor() {
        return color.getTextColor();
    }
}
