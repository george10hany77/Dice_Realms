package game.exceptions;

public class DiceRollException extends Exception{
    public DiceRollException(String s)
    {
        super(s);
    }
    public DiceRollException(String s, Throwable t)
    {
        super(s,t);
    }
}
