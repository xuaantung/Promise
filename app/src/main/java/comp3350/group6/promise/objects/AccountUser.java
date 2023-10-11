package comp3350.group6.promise.objects;

public class AccountUser {

    private final Account account;
    private final User user;

    public AccountUser( Account account, User user ) {
        this.account = account;
        this.user = user;
    }

    public int getUserID(){
        return account.getUserID();
    }

    public String getUserName(){
        return user.getName();
    }

    public String getIntro(){
        return user.getIntro();
    }

    public String getEmail(){
        return account.getEmail();
    }

    public boolean equals(AccountUser other) {
        return getUserID() == other.getUserID();
    }

}
