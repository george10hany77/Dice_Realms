package game.dice;

import game.creatures.RealmColor;

public class BlueDice extends Dice{
    public BlueDice()
    {
        super(RealmColor.BLUE);
    }
    public BlueDice(int value)
    {
        super(RealmColor.BLUE,value);
    }
    public String getTextColor() {
        return color.getTextColor();
    }
}
