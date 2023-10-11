//package comp3350.group6.promise.persistence.stub;
//
//import java.util.ArrayList;
//
//import comp3350.group6.promise.objects.Exceptions.DuplicateNotificationException;
//import comp3350.group6.promise.objects.FakeDB;
//import comp3350.group6.promise.objects.Notification;
//import comp3350.group6.promise.objects.enumClasses.NotifType;
//import comp3350.group6.promise.persistence.NotifDao;
//
//public class NotificationImpNoDB implements NotifDao {
//
//    @Override
//    public void addNotif( int senderID, int projectID, int recipientID, NotifType type ) throws DuplicateNotificationException{
//
//        boolean duplicate = false;
//        Notification newNotif = new Notification( senderID, projectID, recipientID, type );
//
//        //check to see if we already have that notification in our database
//        for( int i = 0; i < FakeDB.notifications.size() && !duplicate; i++ ){
//            if( FakeDB.notifications.get( i ).equals( newNotif ) )
//                duplicate = true;
//        }
//
//        if( duplicate )
//            throw new DuplicateNotificationException( "This notification has already been added" );
//        else
//            FakeDB.notifications.add( new Notification( senderID, projectID, recipientID, type ) );
//
//    }
//
//    @Override
//    public void removeNotif( Notification remove ){
//
//        FakeDB.notifications.remove( remove );
//    }
//
//    @Override
//    public ArrayList<Notification> getNotifs(int recipientID ){
//        return FakeDB.notifications;
//    }
//
//}