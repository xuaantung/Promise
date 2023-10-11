package comp3350.group6.promise.presentation.Notification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import comp3350.group6.promise.R;
import comp3350.group6.promise.application.CurrentSession;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Notification;

//All the notifications in the database are printed to the screen
public class NotificationsFragment extends Fragment {

    private ArrayList<Notification> notificationsList;
    private RecyclerView recyclerView;

    private NavController navController;

    public NotificationsFragment() {
        super(R.layout.fragment_notifications);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get user notifications

        notificationsList = Service.notifications.getNotifs(CurrentSession.getAccount().getUserID());

        // Get views from layout
        navController = NavHostFragment.findNavController(this);
        if( notificationsList.isEmpty() ) {
//            ((TextView) view.findViewById(R.id.notificationsTitle)).setText("No new messages");
            NavDirections action = NotificationsFragmentDirections.navigateToNoNotifications();
            navController.navigate(action);
        }

        else {
            recyclerView = view.findViewById(R.id.notifRecyclerView);
            // Set up notification list
            NotificationAdapter.NotificationClickListener listener = getOnClickListener();
            NotificationAdapter adapter = new NotificationAdapter(notificationsList, listener);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
    }

    private NotificationAdapter.NotificationClickListener getOnClickListener() {
        return new NotificationAdapter.NotificationClickListener() {
            @Override
            public void onClick(View view, int position) {
                Notification notification = notificationsList.get(position);

                int senderId = notification.getSenderID();
                int recipientId = notification.getRecipientID();
                int projectId = notification.getProjectID();

                NavDirections action = NotificationsFragmentDirections.selectNotification(senderId, recipientId, projectId);
                navController.navigate(action);
            }
        };
    }

}