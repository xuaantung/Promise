package comp3350.group6.promise.presentation.User;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import comp3350.group6.promise.R;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Exceptions.DuplicateEmailException;
import comp3350.group6.promise.objects.Exceptions.EmptyEmailException;
import comp3350.group6.promise.objects.Exceptions.EmptyPasswordException;
import comp3350.group6.promise.objects.Exceptions.LoginErrorException;

public class RegisterFragment extends Fragment {

    private Toolbar toolbarView;
    private EditText emailInput;
    private EditText nameInput;
    private EditText passwordInput;
    private EditText introInput;
    private Button submitButton;

    private NavController navController;

    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Get views from layout

        navController = NavHostFragment.findNavController(this);

        toolbarView = view.findViewById(R.id.toolbar);
        emailInput = view.findViewById(R.id.email_input);
        nameInput = view.findViewById( R.id.name_input);
        passwordInput = view.findViewById( R.id.password_input);
        introInput = view.findViewById(R.id.intro_input);
        submitButton = view.findViewById(R.id.register_button);

        // Update layout behaviours

        introInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        introInput.setRawInputType(InputType.TYPE_CLASS_TEXT);

        // Set up toolbar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbarView, navController, appBarConfiguration);
        setHasOptionsMenu(true);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {

                String email = emailInput.getText().toString();
                String name  = nameInput.getText().toString();
                String password = passwordInput.getText().toString();
                String intro = introInput.getText().toString();

                handleRegister(email, name, password, intro);

            }
        });

    }

    public void handleRegister(String email, String name, String password, String intro) {
        try {
            //Send all this information to the business layer
            Service.accounts.register(email, name, password, intro);

            //If we don't get any Exceptions, we can go to the user's home page
            NavDirections action = RegisterFragmentDirections.registerSuccess();
            navController.navigate(action);
        }
        catch( DuplicateEmailException e ){
            openDuplicateDialog();
        }
        // TODO: Display messages in UI indicating what went wrong.
        catch( EmptyEmailException e ){
            //do a dialog or something here
            Log.i("anchor", "No email provided.");
        }
        catch( EmptyPasswordException e ){
            //ditto above
            Log.i("anchor", "No password provided.");
        }
        catch( LoginErrorException e ){
            //maybe use the one in LoginFormFragment
            Log.i("anchor", "Credentials do match a registered user.");
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }

    private void openDuplicateDialog(){
        DuplicateEmailDialogFragment errorDialog = new DuplicateEmailDialogFragment();
        errorDialog.show(getParentFragmentManager(), "email exists error message" );
    }

    public static class DuplicateEmailDialogFragment extends AppCompatDialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState ) {

            AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
            NavController navController = NavHostFragment.findNavController(this);

            builder.setMessage( "This email address is already associated to an account. Please log in or try again." )
                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick( DialogInterface dialogInterface, int i ) {
                            //do nothing. Let them edit their email address and try again
                        }
                    })
                    .setNegativeButton("Log In", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick( DialogInterface dialogInterface, int i ) {
                            navController.navigateUp();
                        }
                    });

            return builder.create();
        }

    }

}