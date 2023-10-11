package comp3350.group6.promise.presentation.Project;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import comp3350.group6.promise.R;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Exceptions.EmptyInputException;
import comp3350.group6.promise.objects.Project;

public class EditProjectFragment extends Fragment {

    String title = "Edit Project";
    int projectId;

    EditText nameInputView;
    EditText descriptionInputView;
    Button submitButtonView;
    Toolbar toolbarView;

    NavController navController;

    public EditProjectFragment() {
        super(R.layout.fragment_create_project);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //Get the projectID that was passed in
        projectId = EditProjectFragmentArgs.fromBundle(getArguments()).getProjectId();

        // Obtain views

        navController = Navigation.findNavController(view);

        nameInputView = view.findViewById(R.id.project_name_input);
        descriptionInputView = view.findViewById(R.id.project_description_input);
        submitButtonView = view.findViewById(R.id.create_project_button);
        toolbarView = view.findViewById(R.id.toolbar);

        // Update layout behaviours

        nameInputView.setText(Service.projects.getProjectByID(projectId).getProjectName());
        descriptionInputView.setText(Service.projects.getProjectByID(projectId).getStatement());
        submitButtonView.setText(title);
        toolbarView.setTitle(title);

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
            updateProject(name, description);
            NavDirections action = EditProjectFragmentDirections.editProjectSuccess(projectId);
            navController.navigate(action);
        }
        catch (EmptyInputException e) {
            Toast.makeText(getContext(), "You need a name for your project.", Toast.LENGTH_LONG).show();
        }
    }

    private void updateProject(String name, String description) throws EmptyInputException {
        Project target = Service.projects.getProjectByID(projectId);
        target.setProjectName(name);
        target.setStatement(description);
        Service.projects.updateProject(target);
    }
}