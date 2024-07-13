package game.dice;

import game.creatures.RealmColor;

public class ArcanePrism extends Dice{

    public ArcanePrism()
    {
        super(RealmColor.WHITE);
    }
    public ArcanePrism(int value)
    {
        super(RealmColor.WHITE,value);
    }
    
    @Override
    public String getTextColor() {
        return color.getTextColor();
    }
}
