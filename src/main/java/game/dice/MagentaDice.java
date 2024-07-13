package game.dice;

import game.creatures.RealmColor;

public class MagentaDice extends Dice{
    public MagentaDice()
    {
        super(RealmColor.MAGENTA);
    }
    public MagentaDice(int value)
    {
        super(RealmColor.MAGENTA,value);
    }
    public String getTextColor() {
        return color.getTextColor();
    }

}
