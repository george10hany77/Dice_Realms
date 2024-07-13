package game.engine;

import game.creatures.Creature;
import game.creatures.RealmColor;
import game.dice.Dice;

public class Move implements Comparable {  // must implement comparable
    private Dice attackDice;
    private Creature monster;

    public Move(Dice attackDice,Creature monster) {
        this.attackDice = attackDice;
        this.monster = monster;
    }
    public Dice getDice()
    {
        return attackDice;
    }
    public Creature getCreature()
    {
        return monster;
    }
    @Override
    public int compareTo(Object o) {
        Move temp = (Move) o;
        RealmColor a = this.getDice().getRealm();
        RealmColor b = temp.getDice().getRealm();
        if(a.ordinal() < b.ordinal())
            return 1;
        else if(a.ordinal() > b.ordinal())
            return -1;
        else
        {
            int vala = this.getDice().getValue();
            int valb = temp.getDice().getValue();
            if(a == RealmColor.RED){
                if(vala < valb)
                    return 1;
                else if(vala >valb)
                    return -1;
                else
                    return 0;
            }
            else if(vala > valb)
                return -1;
            else if(vala<valb)
            {
                return 1;
            }
            else
                return 0;
        }

        
    }

    public String toString(){

        return "attackDice_Colour "+attackDice.getRealm()+" value "+attackDice.getValue();
    }

}
