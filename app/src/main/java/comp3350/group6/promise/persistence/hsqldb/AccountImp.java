/*
 * Please ignore this class. We started implementing before realizing
 * we don't need a functioning database until iteration 2 so we switched
 * to using the fake databases in the "stub" folder
 */

package comp3350.group6.promise.persistence.hsqldb;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import comp3350.group6.promise.objects.Account;
import comp3350.group6.promise.objects.AccountUser;
import comp3350.group6.promise.objects.Exceptions.DuplicateEmailException;
import comp3350.group6.promise.persistence.AccountDao;
import comp3350.group6.promise.util.DBConnectorUtil;

public class AccountImp implements AccountDao {

    @Override
    public void createAccount(String email, String password, int userId) throws DuplicateEmailException {
        try (final Connection cnn = DBConnectorUtil.getConnection()) {
            assert cnn != null;
            password = new String(Base64.encodeBase64URLSafe(password.getBytes(StandardCharsets.UTF_8)), Charset.defaultCharset());
            PreparedStatement preparedStatement = cnn.prepareStatement("insert into Account values (?,?,?)");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        }

        catch( SQLIntegrityConstraintViolationException e ){
            throw new DuplicateEmailException( e.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int changePassword(int userId, String oldPassword, String newPassword){
        try (final Connection cnn = DBConnectorUtil.getConnection()) {
            assert cnn != null;
            oldPassword = new String(Base64.encodeBase64URLSafe(oldPassword.getBytes(StandardCharsets.UTF_8)), Charset.defaultCharset());
            newPassword = new String(Base64.encodeBase64URLSafe(newPassword.getBytes(StandardCharsets.UTF_8)), Charset.defaultCharset());
            PreparedStatement preparedStatement = cnn.prepareStatement("update Account set password = ? where userId = ? and password = ?");
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, oldPassword);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    @Override
    public boolean accountExists( String email ) {

        try (final Connection cnn = DBConnectorUtil.getConnection()) {
            assert cnn != null;
            PreparedStatement preparedStatement = cnn.prepareStatement("select * from account where email = ?");
            preparedStatement.setString(1, email);
            ResultSet res = preparedStatement.executeQuery();
            return res.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account getAccountByEmail(String email) {

        Account account = null;

        try (final Connection cnn = DBConnectorUtil.getConnection()) {
            assert cnn != null;
            PreparedStatement preparedStatement = cnn.prepareStatement("select * from account where email = ?");
            preparedStatement.setString(1, email);
            ResultSet res = preparedStatement.executeQuery();
            if( res.next() )
                account = new Account(res.getString("email"), null, res.getInt("userId"));
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }

    @Override
    public Account getAccountByID(int userID){

        Account account = null;

        try (final Connection cnn = DBConnectorUtil.getConnection()) {
            assert cnn != null;
            PreparedStatement preparedStatement = cnn.prepareStatement("select * from account where userID = ?");
            preparedStatement.setInt( 1, userID );
            ResultSet res = preparedStatement.executeQuery();
            if( res.next() )
                account = new Account(res.getString("email"), null, res.getInt("userId"));
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return account;

    }

    @Override
    public boolean passwordsMatch(String email, String password) {
        try (final Connection cnn = DBConnectorUtil.getConnection()) {
            password = new String(Base64.encodeBase64URLSafe(password.getBytes(StandardCharsets.UTF_8)), Charset.defaultCharset());
            assert cnn != null;
            PreparedStatement preparedStatement = cnn.prepareStatement("select * from account where email = ? and password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
