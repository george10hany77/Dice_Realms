package game.exceptions;

public class InvalidMoveException extends Exception{
    public InvalidMoveException(String s)
    {
        super(s);
    }
    public InvalidMoveException(String s, Throwable t)
    {
        super(s,t);
    }
}
