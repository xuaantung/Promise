package comp3350.group6.promise.objects;

import androidx.annotation.NonNull;

import comp3350.group6.promise.objects.enumClasses.NotifType;
import lombok.Data;

@Data
public class Notification {
    private int senderID;         //userID of the user that sent the notification
    private int projectID;      //ID of the project this notification refers to
    private int recipientID;    //userID of the user that's receiving the notification
    private NotifType type;     //A notification is either a request or an invite

    public Notification(int senderID, int projectID, int recipientID, NotifType type) {

        this.senderID = senderID;
        this.projectID = projectID;
        this.recipientID = recipientID;
        this.type = type;

    }

    public int getSenderID() {
        return senderID;
    }

    public int getProjectID() {
        return projectID;
    }

    public int getRecipientID() {
        return recipientID;
    }

    public NotifType getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString(){

        return "senderID: " + senderID +
                ", projectID: " + projectID +
                ", recipientID: " + recipientID +
                ", type: " + type.name();

    }

    @Override
    public boolean equals( Object other ){

        if( this == other ){
            return true;
        }

        if( other == null || getClass() != other.getClass() ){
            return false;
        }

        Notification notification = (Notification) other;

        return senderID == notification.projectID &&
                projectID == notification.projectID &&
                recipientID == notification.recipientID &&
                type == notification.type;

    }

}
