package comp3350.group6.promise.presentation.Project;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import comp3350.group6.promise.R;
import comp3350.group6.promise.application.CurrentSession;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.business.ProjectService;
import comp3350.group6.promise.business.TaskService;
import comp3350.group6.promise.objects.Access;
import comp3350.group6.promise.objects.Project;
import comp3350.group6.promise.objects.Task;
import comp3350.group6.promise.objects.enumClasses.AccessRole;
import comp3350.group6.promise.presentation.Task.TaskAdapter;

public class ProjectFragment extends Fragment {

    private Access access;
    private Project project;
    private MutableLiveData<List<Task>> taskListIP = new MutableLiveData<>();
    private MutableLiveData<List<Task>> taskListFinished = new MutableLiveData<>();
    private TaskAdapter taskListAdapterIP;
    private TaskAdapter taskListAdapterFinished;

    private Toolbar toolbarView;
    private TextView projectDescriptionView;
    private RecyclerView taskRecyclerViewIP;
    private RecyclerView taskRecyclerViewFinished;

    private FloatingActionButton fab;

    private NavController navController;

    public ProjectFragment() {
        super(R.layout.fragment_project);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Get project ID from navigation arguments

        int projectId = ProjectFragmentArgs.fromBundle(getArguments()).getProjectId();

        // Get project data

        if (projectId != -1) {
            project = ProjectService.getInstance().getProjectByID(projectId);
            taskListIP.setValue(TaskService.getInstance().getTasksByProjectId(projectId, 1));
            taskListFinished.setValue(TaskService.getInstance().getTasksByProjectId(projectId, 0));
        }

        // set Access
        access = Service.accesses.getAccessByIDs(CurrentSession.getAccount().getUserID(), projectId);

        // Get views from layout

        navController = Navigation.findNavController(view);

        toolbarView = view.findViewById(R.id.toolbar);
        projectDescriptionView = view.findViewById(R.id.project_page_desc);

        fab = getActivity().findViewById(R.id.fab);

        taskRecyclerViewIP = view.findViewById(R.id.task_recycler_in_progress);
        taskRecyclerViewFinished = view.findViewById(R.id.task_recycler_finished);

        // Update layout content

        projectDescriptionView.setText(project.getStatement());

        IncompleteTaskClickListener incompleteTaskClickListener = new IncompleteTaskClickListener();
        taskListAdapterIP = new TaskAdapter(getContext(), taskListIP.getValue(), incompleteTaskClickListener, incompleteTaskClickListener);
        taskRecyclerViewIP.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        taskRecyclerViewIP.setAdapter(taskListAdapterIP);

        taskListIP.observe(getViewLifecycleOwner(), tasks -> {
            taskListAdapterIP.notifyDataSetChanged();
        });

        CompleteTaskClickListener completeTaskClickListener = new CompleteTaskClickListener();
        taskListAdapterFinished = new TaskAdapter(getContext(), taskListFinished.getValue(), completeTaskClickListener, completeTaskClickListener);
        taskRecyclerViewFinished.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        taskRecyclerViewFinished.setAdapter(taskListAdapterFinished);

        taskListFinished.observe(getViewLifecycleOwner(), tasks -> {
            taskListAdapterFinished.notifyDataSetChanged();
        });

        // Update layout behaviours

        initializeToolbar();
        initializeFab();

    }

    public void handleAddTask(int projectId) {
        NavDirections action = ProjectFragmentDirections.createTask(projectId);
        navController.navigate(action);
    }

    // Task List Methods

    private class IncompleteTaskClickListener implements TaskAdapter.OnTaskClickListener, TaskAdapter.OnTaskLongClickListener {

        @Override
        public void onTaskClick(int position) {
            int taskId = taskListIP.getValue().get(position).getTaskId();
            NavDirections action = ProjectFragmentDirections.selectTask(taskId);
            navController.navigate(action);
        }

        @Override
        public void onLongTaskClick(int position) {
            // Business layer call to mark task as complete
            Toast.makeText(getContext(), "Task has been marked complete", Toast.LENGTH_SHORT).show();
            int taskId = taskListIP.getValue().get(position).getTaskId();
            Service.tasks.logTask(taskId);
            refreshTaskLists();
        }
    }

    private class CompleteTaskClickListener implements TaskAdapter.OnTaskClickListener, TaskAdapter.OnTaskLongClickListener {

        @Override
        public void onTaskClick(int position) {
            int taskId = taskListFinished.getValue().get(position).getTaskId();
            NavDirections action = ProjectFragmentDirections.selectTask(taskId);
            navController.navigate(action);
        }

        @Override
        public void onLongTaskClick(int position) {
            // Business layer call to mark task as incomplete
            Toast.makeText(getContext(), "Task has been marked incomplete", Toast.LENGTH_SHORT).show();
            int taskId = taskListFinished.getValue().get(position).getTaskId();
            Service.tasks.logTask(taskId);
            refreshTaskLists();
        }
    }

    // Toolbar Methods

    private void initializeToolbar() {
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        setHasOptionsMenu(true);
        activity.setTitle(project.getProjectName());
        activity.setSupportActionBar(toolbarView);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbarView, navController, appBarConfiguration);
        NavigationUI.setupActionBarWithNavController(activity, navController, appBarConfiguration);
    }

    private void initializeFab() {
        fab.setOnClickListener( fab -> {
                handleAddTask(project.getProjectID());
            }
        );
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.project_toolbar_menu, menu);

        // hide certain options depending on the user's Access(role) in a project
        MenuItem edit = menu.findItem(R.id.action_edit_project);
        MenuItem delete = menu.findItem(R.id.action_delete_project);

        if (access.getRole().equals(AccessRole.MEMBER.name())) {
            // normal members can't edit or delete a project
            edit.setVisible(false);
            delete.setVisible(false);
        } else if (access.getRole().equals(AccessRole.ADMIN.name())) {
            // admins can't delete a project
            delete.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_invite_user) {
            NavDirections action = ProjectFragmentDirections.actionInviteToProject(project.getProjectID());
            navController.navigate(action);
            return true;
        }
        else if(id == R.id.action_delete_project) {
            deleteDialogue();
            return true;
        }
        else if(id == R.id.action_edit_project) {
            NavDirections action = ProjectFragmentDirections.actionEditProject(project.getProjectID());
            navController.navigate(action);
            return true;
        }
        else if(id == R.id.action_view_members) {
            NavDirections action = ProjectFragmentDirections.actionViewMembers(project.getProjectID());
            navController.navigate(action);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // popup dialogue for the project delete option
    private void deleteDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to delete \"" + project.getProjectName() + "\"?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // delete current project
                        Service.projects.deleteProject(project);
                        // go back to dashboard fragment
                        NavDirections action = ProjectFragmentDirections.actionDeleteProject();
                        navController.navigate(action);
                    }
                });

        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void refreshTaskLists() {

        List<Task> taskListIPValue = taskListIP.getValue();
        List<Task> taskListFinishedValue = taskListFinished.getValue();

        List<Task> updatedIP = TaskService.getInstance().getTasksByProjectId(project.getProjectID(), 1);
        List<Task> updatedFinished = TaskService.getInstance().getTasksByProjectId(project.getProjectID(), 0);

        taskListIPValue.clear();
        taskListFinishedValue.clear();

        taskListIPValue.addAll(updatedIP);
        taskListFinishedValue.addAll(updatedFinished);

        taskListFinished.setValue(taskListFinishedValue);
        taskListIP.setValue(taskListIPValue);

    }

    @Override
    public void onResume() {
        super.onResume();
        initializeFab();
        refreshTaskLists();
    }

}