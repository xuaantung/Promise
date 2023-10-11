package comp3350.group6.promise.presentation.Project;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import comp3350.group6.promise.R;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Access;
import comp3350.group6.promise.application.CurrentSession;
import comp3350.group6.promise.objects.Exceptions.EmptyInputException;
import comp3350.group6.promise.objects.Project;
import comp3350.group6.promise.objects.enumClasses.AccessRole;

public class CreateProjectFragment extends Fragment {

    EditText nameInputView;
    EditText descriptionInputView;
    Button submitButtonView;
    Toolbar toolbarView;

    NavController navController;

    public CreateProjectFragment() {
        super(R.layout.fragment_create_project);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Obtain views

        navController = Navigation.findNavController(view);

        nameInputView = view.findViewById(R.id.project_name_input);
        submitButtonView = view.findViewById(R.id.create_project_button);
        descriptionInputView = view.findViewById(R.id.project_description_input);
        toolbarView = view.findViewById(R.id.toolbar);

        // Update layout behaviours

        submitButtonView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View submitButtonView ) {
                String name = nameInputView.getText().toString();
                String description = descriptionInputView.getText().toString();
                handleSubmit(name, description);
            }
        });

        // Keep "next" keyboard action while allowing multiline text
        descriptionInputView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        descriptionInputView.setRawInputType(InputType.TYPE_CLASS_TEXT);

        // Setup toolbar with navigation controller
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbarView, navController, appBarConfiguration);

    }

    private void handleSubmit(String name, String description) {
        // Return to projects page if creation is successful
        try {
            createProject(name, description);
            NavDirections action = CreateProjectFragmentDirections.createProjectSuccess();
            navController.navigate(action);
        }
        catch (EmptyInputException e) {
            Toast.makeText(getContext(), "You need a name for your project.", Toast.LENGTH_LONG).show();
        }
    }

    private void createProject(String name, String description) throws EmptyInputException {
        Project newProject = Service.projects.insertProject(new Project(name, description));
        Access newAccess = new Access(newProject.getProjectID(), CurrentSession.getAccount().getUserID());
        newAccess.setRole(AccessRole.CREATOR.name());
        Service.accesses.insertAccess(newAccess);
    }
}