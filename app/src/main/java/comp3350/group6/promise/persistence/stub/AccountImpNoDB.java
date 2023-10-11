//package comp3350.group6.promise.persistence.stub;
//
//import comp3350.group6.promise.objects.Account;
//import comp3350.group6.promise.objects.Exceptions.DuplicateEmailException;
//import comp3350.group6.promise.objects.FakeDB;
//import comp3350.group6.promise.persistence.AccountDao;
//
//public class AccountImpNoDB implements AccountDao {
//
//    @Override
//    public void createAccount( String email, String password, int userID ) throws DuplicateEmailException {
//
//
//        if( accountExists( email ) )
//            throw new DuplicateEmailException("This account already exists.\n");
//
//        else
//            FakeDB.accounts.add( new Account( email, password, userID ) );
//
//    }
//
//    @Override
//    public int changePassword( int userID, String oldPassword, String newPassword ){
//
//        int res = 0;
//        int index = getIndexByUserID( userID );
//
//        if( index != -1 ){
//
//            if( FakeDB.accounts.get( index ).getPassword().equals( oldPassword ) ){
//
//                FakeDB.accounts.get( index ).setPassword( newPassword );
//                res = 1;
//
//            }
//
//        }
//
//        return res;
//
//    }
//
//    @Override
//    public boolean accountExists( String email ){
//        return getIndexByEmail( email ) != -1;
//    }
//
//    @Override
//    public Account getAccountByEmail(String email){
//
//        Account returnAccount = null;
//        int index = getIndexByEmail( email );
//
//        if( index != -1 )
//            returnAccount = FakeDB.accounts.get( index );
//
//        return returnAccount;
//
//    }
//
//    @Override
//    public Account getAccountByID(int userID){
//        Account returnAccount = null;
//        int index = getIndexByUserID(userID);
//
//        if(index != -1)
//            returnAccount = FakeDB.accounts.get(index);
//        return returnAccount;
//    }
//
//    @Override
//    public boolean passwordsMatch( String email, String password ){
//
//        boolean match;
//        int index = getIndexByEmail( email );
//        if( index == -1 )
//            match = false;
//        else
//            match = FakeDB.accounts.get( index ).getPassword().equals( password );
//        return match;
//    }
//
//    private int getIndexByEmail(String email ){
//
//        int index = -1; //default if the account doesn't exist
//        boolean exists = false;
//
//        for( int i = 0; i < FakeDB.accounts.size() && !exists; i++ ) {
//            if ( FakeDB.accounts.get( i ).getEmail().equals( email ) ) {
//
//                index = i;
//                exists = true;
//
//            }
//        }
//
//        return index;
//
//    }
//
//    private int getIndexByUserID( int userID ){
//
//        int index = -1;
//        boolean exists = false;
//
//        for( int i = 0; i < FakeDB.accounts.size() && !exists; i++ ){
//            if( FakeDB.accounts.get( i ).getUserID() == userID ){
//
//                index = i;
//                exists = true;
//
//            }
//        }
//
//        return index;
//
//    }
//
//}
