package game.dice;

import game.creatures.RealmColor;

public class YellowDice extends Dice{
    public YellowDice()
    {
        super(RealmColor.YELLOW);
    }
    public YellowDice(int value)
    {
        super(RealmColor.YELLOW,value);
    }
    public String getTextColor() {
        return color.getTextColor();
    }
}
