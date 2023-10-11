package comp3350.group6.promise.presentation.Notification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import comp3350.group6.promise.R;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Exceptions.DuplicateAccessException;
import comp3350.group6.promise.objects.Notification;
import comp3350.group6.promise.objects.enumClasses.NotifType;

public class NotificationsDetailFragment extends Fragment {

    private TextView messageText;
    private Button acceptButton;
    private Button rejectButton;

    private NavController navController;

    public NotificationsDetailFragment() {
        super(R.layout.fragment_invite_response);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Get notification data from arguments

        int senderId = NotificationsDetailFragmentArgs.fromBundle(getArguments()).getSenderId();
        int recipientId = NotificationsDetailFragmentArgs.fromBundle(getArguments()).getRecipientId();
        int projectId = NotificationsDetailFragmentArgs.fromBundle(getArguments()).getProjectId();

        Notification notification = new Notification(senderId, projectId, recipientId, NotifType.INVITE);

        String senderName = Service.accountUser.getUserByAccountID(senderId).getUserName();
        String projectName = Service.projects.getProjectByID(projectId).getProjectName();

        // Obtain views from layout

        navController = NavHostFragment.findNavController(this);

        messageText = view.findViewById(R.id.notifDetailMessage);
        acceptButton = view.findViewById(R.id.acceptInviteButton);
        rejectButton = view.findViewById(R.id.rejectInviteButton);

        // Update layout content

        String viewMessage = String.format("%s has invited you to work on %s.", senderName, projectName);

        messageText.setText( viewMessage );

        // Update layout behaviours

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Consider going to go the project itself?
                try {
                    Service.notifications.accept( notification );
                    navController.navigateUp();
                } catch (DuplicateAccessException err) {
                    // TODO: Maybe move this code when inviting a user? -Dani
                    Toast.makeText(getContext(), "You are already a member of this project.", Toast.LENGTH_SHORT).show();
                    Service.notifications.reject( notification );
                    navController.navigateUp();
                }
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service.notifications.reject( notification );
                navController.navigateUp();
            }
        });

    }

}