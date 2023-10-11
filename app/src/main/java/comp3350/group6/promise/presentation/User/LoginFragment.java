package comp3350.group6.promise.presentation.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import comp3350.group6.promise.R;

public class LoginFragment extends Fragment {

    TextView registerTextView;
    NavController navController;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Get views from layout

        navController = NavHostFragment.findNavController(this);
        registerTextView = view.findViewById(R.id.registerLink);

        // Update layout behaviours

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = LoginFragmentDirections.register();
                navController.navigate(action);
            }
        });

    }

}