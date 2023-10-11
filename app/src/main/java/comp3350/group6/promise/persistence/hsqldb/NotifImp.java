/*
 * Please ignore this class. We started implementing before realizing
 * we don't need a functioning database until iteration 2 so we switched
 * to using the fake databases in the "stub" folder
 */

package comp3350.group6.promise.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import comp3350.group6.promise.objects.Exceptions.DuplicateNotificationException;
import comp3350.group6.promise.objects.Exceptions.PersistenceException;
import comp3350.group6.promise.objects.Notification;
import comp3350.group6.promise.objects.enumClasses.NotifType;
import comp3350.group6.promise.persistence.NotifDao;
import comp3350.group6.promise.util.DBConnectorUtil;

public class NotifImp implements NotifDao {

    @Override
    public void addNotif( int senderID, int projectID, int recipientID, NotifType type) throws DuplicateNotificationException {

        String insertStatement = "INSERT INTO Notification VALUES(?,?,?,?)";
        Log.i("anchor", "type.toString(): " + type.toString());
        Log.i("anchor", "type.name():" + type.name());
        try (final Connection cnn = DBConnectorUtil.getConnection();
            PreparedStatement pStatement = cnn.prepareStatement(insertStatement)){

            pStatement.setInt(1, senderID);
            pStatement.setInt(2, projectID);
            pStatement.setInt(3, recipientID);
            pStatement.setString(4, type.toString());
            pStatement.executeUpdate();

        }

        catch( SQLIntegrityConstraintViolationException e ){
            throw new DuplicateNotificationException( e.getMessage() );
        }

        catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void removeNotif( Notification remove ) {

        String deleteStatement = "DELETE FROM Notification WHERE senderID = ? AND projectID = ? AND recipientID = ?";
        try (final Connection cnn = DBConnectorUtil.getConnection();
            PreparedStatement pStatement = cnn.prepareStatement(deleteStatement)){
            pStatement.setInt( 1, remove.getSenderID() );
            pStatement.setInt( 2, remove.getProjectID() );
            pStatement.setInt( 3, remove.getRecipientID() );
            pStatement.executeUpdate();
        }

        catch(SQLException e){
            throw new PersistenceException(e);
        }

    }

    @Override
    public ArrayList<Notification> getNotifs(int recipientID) {

        ArrayList<Notification> notifs;

        int sender;
        int projectID;
        int reciever;
        NotifType type;

        String selectStatement = "SELECT * FROM Notification WHERE recipientID = ?";
        try (final Connection cnn = DBConnectorUtil.getConnection();
            PreparedStatement pStatement = cnn.prepareStatement( selectStatement ) ) {
            pStatement.setInt(1, recipientID);
            ResultSet resultSet = pStatement.executeQuery();
            notifs = new ArrayList<Notification>();

            while (resultSet.next()) {

                sender = resultSet.getInt("senderID");
                projectID = resultSet.getInt("projectID");
                reciever = resultSet.getInt("recipientID");
                type = NotifType.valueOf( resultSet.getString("type") );
                notifs.add(new Notification(sender, projectID, reciever, type));

            }

        }

        catch (SQLException e){
            throw new PersistenceException(e);
        }

        return notifs;

    }

}
