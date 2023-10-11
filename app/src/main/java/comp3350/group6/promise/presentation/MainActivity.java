package comp3350.group6.promise.presentation;

import static comp3350.group6.promise.util.FileSystemUtil.copyDatabaseToDevice;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import comp3350.group6.promise.R;
import comp3350.group6.promise.RootGraphDirections;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.util.UserPrefsUtil;

public class MainActivity extends AppCompatActivity {

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_nav_fragment);
        navController = navHostFragment.getNavController();

        copyDatabaseToDevice(this);
        restoreLogIn();
    }

    public void restoreLogIn() {
        try {
            UserPrefsUtil.UserCredentials saved = UserPrefsUtil.getSavedUserCredentials(this);
            String savedEmail = saved.getEmail();
            String savedPassword = saved.getPassword();

            if(savedEmail != null && savedPassword != null) {
                Service.accounts.login(savedEmail, savedPassword);

                NavDirections action = RootGraphDirections.dashboard();
                navController.navigate(action);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}