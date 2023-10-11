package comp3350.group6.promise.objects.Exceptions;

public class PersistenceException extends RuntimeException{
    public PersistenceException(final Exception cause){
        super(cause);
    }

    public PersistenceException() {

    }
}
