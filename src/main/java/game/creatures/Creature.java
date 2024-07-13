package game.creatures;

import game.dice.Dice;
import game.exceptions.InvalidMoveException;
import game.collectibles.RewardType;
import game.engine.Move;
import game.exceptions.DiceRollException;;
public abstract class Creature {
     boolean isAttackable;
     int score;

    public Creature() {
        isAttackable = true;
        score = 0;
    }

    public abstract RewardType[] attack(Dice dice) throws InvalidMoveException, NullPointerException, DiceRollException;

    public boolean isAttackable() {
        return isAttackable;
    }

    abstract void updateScore(int value);

    public int getScore() {
        return score;
    }

    public abstract String toString();

    public abstract Move[] getPossibleMovesForADie(Dice dice) throws NullPointerException, DiceRollException;

    public abstract Move[] getPossibleMoves();

}
