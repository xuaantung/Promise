package comp3350.group6.promise.objects.Exceptions;

public class DuplicateAccessException extends RuntimeException{

    // this exception should be caught when inserting an access by invites
    public DuplicateAccessException(String err){
        super(err);
    }
}
