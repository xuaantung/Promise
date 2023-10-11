package comp3350.group6.promise.presentation.Notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.group6.promise.R;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotifViewHolder> {

    private final ArrayList<Notification> notificationsList;
    private NotificationClickListener listener;

    public NotificationAdapter( ArrayList<Notification> notificationsList, NotificationClickListener listener ){
        this.notificationsList = notificationsList;
        this.listener = listener;
    }

    public class NotifViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView notifText;
        public NotifViewHolder( final View view ){

            super( view );
            notifText = view.findViewById( R.id.notifTextView );
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick( view, getAbsoluteAdapterPosition() );
        }
    }

    @NonNull
    @Override
    public NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from( parent.getContext() ).inflate(R.layout.notification_list_item, parent, false );
        return new NotifViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotifViewHolder holder, int position) {

        int senderID = notificationsList.get( position ).getSenderID();
        String senderName = Service.accountUser.getUserByAccountID( senderID ).getUserName();

        int projectID = notificationsList.get( position ).getProjectID();
        String projectName = Service.projects.getProjectByID( projectID ).getProjectName();

        String notifMessage = senderName + " has invited you to work on \"" + projectName + "\"";

        holder.notifText.setText( notifMessage );
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public interface NotificationClickListener{

        void onClick( View view, int position );

    }
}
