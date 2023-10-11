package comp3350.group6.promise.presentation.User;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import comp3350.group6.promise.R;
import comp3350.group6.promise.RootGraphDirections;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Exceptions.EmptyEmailException;
import comp3350.group6.promise.objects.Exceptions.EmptyPasswordException;
import comp3350.group6.promise.objects.Exceptions.LoginErrorException;
import comp3350.group6.promise.util.UserPrefsUtil;


public class LoginFormFragment extends Fragment {

    private Button submitButtonView;
    private EditText emailInputView;
    private EditText passwordInputView;

    public LoginFormFragment() {
        super(R.layout.fragment_login_form);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Get views from layout

        emailInputView = (EditText) view.findViewById( R.id.loginEmailInput );
        passwordInputView = (EditText) view.findViewById( R.id.loginPasswordInput );
        submitButtonView = (Button) view.findViewById( R.id.signOutButton);

        // Update layout behaviours

        submitButtonView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                handleSubmit();
            }
        });

    }

    private void handleSubmit() {
        String email = emailInputView.getText().toString();
        String password = passwordInputView.getText().toString();

        //Send input to the business layer
        try{
            Service.accounts.login(email, password);
            UserPrefsUtil.saveUserCredentials(email, password, getActivity());

            //if no Exception was thrown, we'll go back to the user's home page
            NavDirections action = LoginFragmentDirections.loginSuccess();
            NavHostFragment.findNavController(this).navigate(action);
        }
        catch( LoginErrorException e ){
            openLoginErrorDialog();
        }

        catch( EmptyEmailException e ){
            Log.i("anchor", "Empty email");
        }

        catch( EmptyPasswordException e ){
            Log.i("anchor", "Empty password");
        }
        catch( Exception e ){
            e.printStackTrace();
        }

    }

    private void openLoginErrorDialog() {
        LoginErrorDialogueFragment errorDialogue = new LoginErrorDialogueFragment();
        errorDialogue.show(getActivity().getSupportFragmentManager(), "loginError");
    }

    public static class LoginErrorDialogueFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            NavController navController = NavHostFragment.findNavController(this);

            builder.setMessage( "The email and password provided you provided does not match a registered user." )
                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick( DialogInterface dialogInterface, int i ) {
                            //do nothing. Let them edit the username and try again
                        }
                    })
                    .setNeutralButton("Create Account", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NavDirections action = RootGraphDirections.register();
                            navController.navigate(action);
                        }
                    });

            return builder.create();
        }

    }

}