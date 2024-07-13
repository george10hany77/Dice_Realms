package game.exceptions;

public class UnavailablePowerup extends Exception {
    public UnavailablePowerup(String s)
    {
        super(s);
    }
    public UnavailablePowerup(String s, Throwable t)
    {
        super(s,t);
    }
}
