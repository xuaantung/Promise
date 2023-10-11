package comp3350.group6.promise.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import comp3350.group6.promise.R;
import comp3350.group6.promise.RootGraphDirections;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.util.UserPrefsUtil;

public class SettingsFragment extends Fragment {

    Button signOutButton;
    NavController rootNavController;

    public SettingsFragment() {
        super(R.layout.fragment_settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get views from layout

        rootNavController = Navigation.findNavController(getActivity(), R.id.main_nav_fragment);
        signOutButton = view.findViewById(R.id.signOutButton);

        // Update layout behaviours

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignOut();
            }
        });
    }

    private void handleSignOut() {
        Service.accounts.logout();
        UserPrefsUtil.clearSavedUserCredentials(getActivity());
        NavDirections action = RootGraphDirections.login();
        rootNavController.navigate(action);
    }
}
