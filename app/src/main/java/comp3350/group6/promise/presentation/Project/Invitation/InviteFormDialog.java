package comp3350.group6.promise.presentation.Project.Invitation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.business.AccessService;
import comp3350.group6.promise.objects.AccountUser;
import comp3350.group6.promise.objects.Exceptions.AccountDNException;
import comp3350.group6.promise.objects.Exceptions.DuplicateNotificationException;
import comp3350.group6.promise.objects.User;
import comp3350.group6.promise.presentation.User.UserSelectionDialog;

public class InviteFormDialog extends UserSelectionDialog {

    int projectId;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        projectId = InviteFormDialogArgs.fromBundle(getArguments()).getProjectId();
        submitButton.setText("Invite Users");
        applySearchFilter();
    }

    protected boolean handleSubmit() {

        List<AccountUser> selectedUsers = super.model.getSelectedUsers().getValue();
        int failedSendCount = 0;

        for(int i = 0; i < selectedUsers.size(); i++) {
            AccountUser user = selectedUsers.get(i);
            boolean inviteHasBeenSent = true;

            try {
                Service.notifications.invite(user.getEmail(), projectId);
            }
            catch(AccountDNException e) {
                inviteHasBeenSent = false;
            }
            catch(DuplicateNotificationException e) {
                // Probably not necessary to notify the user in this case
            }

            if(inviteHasBeenSent) {
                super.model.removeUserFromSelection(i);
            }
            else {
                failedSendCount++;
            }
        }

        if(failedSendCount > 0) {
            Toast.makeText(getActivity(), String.format("Failed to invite %d users.", failedSendCount), Toast.LENGTH_SHORT).show();
            return false;
        }

        else {
            super.navController.navigateUp();
            Toast.makeText(getActivity(), "Invites sent successfully.", Toast.LENGTH_SHORT).show();
            return true;
        }

    }

    protected void applySearchFilter() {
        // Prevent users from inviting other project members
        ArrayList<Integer> excludedUsers = new ArrayList<>();
        try {
            List<User> projectUsers = AccessService.getInstance().getUsers(projectId);
            for(User user : projectUsers) {
                excludedUsers.add(user.getUserID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.model.setExcludedUsers(excludedUsers);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        getViewModelStore().clear();
    }
}