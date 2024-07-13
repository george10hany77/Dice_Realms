package game.exceptions;

// I cannot inherit from Exception
// as in the test cases it is not handled between try and catch, and I cannot change the test files
public class InvalidDragonNumber extends RuntimeException{
    public InvalidDragonNumber(String s){super(s);}
    public InvalidDragonNumber(String s, Throwable t){super(s, t);}
}
