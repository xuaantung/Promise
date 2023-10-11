package comp3350.group6.promise.objects;

import lombok.Data;

@Data
public class Account {

    private final String email;
    private String password;
    private final int userId;

    public Account( String email, String password, int userId ){

        this.email = email;
        this.password = password;
        this.userId = userId;

    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public int getUserID(){
        return userId;
    }

    public void setPassword( String password ){

        this.password = password;

    }

    @Override
    public boolean equals( Object other ){

        boolean isEqual;
        boolean emailMatch;
        boolean idMatch;

        if( this == other ){
            isEqual = true;
        }

        else if( other == null || getClass() != other.getClass() )
            isEqual = false;

        else {

            Account otherAccount = ( Account ) other;
            emailMatch = email.equals( otherAccount.getEmail() );
            idMatch    = userId == otherAccount.getUserID();
            isEqual = emailMatch && idMatch;

        }

        return isEqual;

    }

}
