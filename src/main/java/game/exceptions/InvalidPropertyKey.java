package game.exceptions;

public class InvalidPropertyKey extends Exception{
    public InvalidPropertyKey(String s)
    {
        super(s);
    }
    public InvalidPropertyKey(String s, Throwable t)
    {
        super(s,t);
    }

}
