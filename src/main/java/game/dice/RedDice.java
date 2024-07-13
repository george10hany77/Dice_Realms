package game.dice;

import game.creatures.DragonNumber;
import game.creatures.RealmColor;
import game.exceptions.InvalidDragonNumber;

public class RedDice extends Dice{
    private DragonNumber dragonNumber;
    public RedDice() {
        super(RealmColor.RED);
        this.dragonNumber = null;
    }
    public RedDice(int value) {
        super(RealmColor.RED, value);
        this.dragonNumber = null;
    }
    public RedDice(int value, DragonNumber dragonNumber) {
        super(RealmColor.RED,value);
        this.dragonNumber = dragonNumber;
    }
	public void selectsDragon(int i) throws InvalidDragonNumber {
        if (i <= 0 || i > 4){
            throw new InvalidDragonNumber("This Dragon doesn't exist !");
        }
        dragonNumber = DragonNumber.getDragon(i);
	}
    public DragonNumber getDragonNumber() {
        return dragonNumber;
    }

    public void resetDragonNumber(){
        // to reset the dragon number each time the dice is rolled.
        // it should be also reset in other places !!.
        dragonNumber=null;
    }

    @Override
    public void roll() {
        super.roll();
        resetDragonNumber();
    }

    @Override
    public String getTextColor() {
        return color.getTextColor();
    }
}
