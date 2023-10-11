package comp3350.group6.promise.presentation.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.group6.promise.R;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.AccountUser;
import comp3350.group6.promise.objects.Handle;
import comp3350.group6.promise.objects.Task;
import comp3350.group6.promise.presentation.User.UserSelectionDialog;
import comp3350.group6.promise.presentation.User.AccountUserAdapter;
import comp3350.group6.promise.presentation.User.UserSelectorViewModel;

public class CreateTaskFragment extends Fragment {

    private Toolbar toolbar;
    private EditText nameInput;
    private EditText descriptionInput;
    private EditText deadlineInput;
    private EditText priorityInput;
    private Button submitButton;

    private RecyclerView assigneeListRecycler;
    private UserSelectorViewModel assigneeSelectorModel;

    private NavController navController;

    public CreateTaskFragment() {
        super(R.layout.fragment_create_task);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        assigneeSelectorModel = new ViewModelProvider(this).get(UserSelectorViewModel.class);

        // Get arguments passed to fragment

        int projectId = CreateTaskFragmentArgs.fromBundle(getArguments()).getProjectId();

        // Obtain views from layout

        navController = NavHostFragment.findNavController(this);

        toolbar = view.findViewById(R.id.task_toolBar);
        nameInput = view.findViewById(R.id.task_name_input);
        descriptionInput = view.findViewById(R.id.task_description_input);
        deadlineInput = view.findViewById(R.id.task_estimate_input);
        priorityInput = view.findViewById(R.id.task_priority_input);
        submitButton = view.findViewById(R.id.submit_task_button);

        View assigneePrompt = view.findViewById(R.id.task_assignee_add_message_container);
        assigneeListRecycler = view.findViewById(R.id.task_assignee_recycler);

        AccountUserAdapter assigneeListAdapter = new AccountUserAdapter(
                getContext(),
                assigneeSelectorModel.getSelectedUsers().getValue(),
                assigneeView -> {
                    openUserSelector();
                },
                R.layout.user_list_section_item
        );

        assigneeListRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        assigneeListRecycler.setAdapter(assigneeListAdapter);

        final Observer<List<AccountUser>> selectedListObserver = new Observer<List<AccountUser>>() {
            @Override
            public void onChanged(@Nullable final List<AccountUser> selectionList) {
                assigneeListRecycler.setVisibility(selectionList.isEmpty() ? View.GONE : View.VISIBLE);
                assigneePrompt.setVisibility(selectionList.isEmpty() ? View.VISIBLE : View.GONE);
                assigneeListAdapter.notifyDataSetChanged();
            }
        };

        assigneeSelectorModel.getSelectedUsers().observe(getViewLifecycleOwner(), selectedListObserver);

        assigneePrompt.setOnClickListener(aView -> openUserSelector());

        // Update layout behaviours

        submitButton.setOnClickListener(button -> {
            handleSubmit(projectId);
        });

        // Set up toolbar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        setHasOptionsMenu(true);
    }

    private void handleSubmit(int projectId) {

        String name = nameInput.getText().toString();
        String description = descriptionInput.getText().toString();
        String deadline = deadlineInput.getText().toString();
        String priority = priorityInput.getText().toString();
        Timestamp deadlineTime;

        if (TextUtils.isEmpty(name)) {
            nameInput.setError("This item cannot be empty");
            return;
        } else if (TextUtils.isEmpty(priority)) {
            priorityInput.setError("This item cannot be empty");
            return;
        } else if (TextUtils.isEmpty(deadline)) {
            deadlineInput.setError("This item cannot be empty");
            return;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, Integer.valueOf(deadline));
        deadlineTime = new Timestamp(c.getTimeInMillis());
        int p = Integer.valueOf(priority);

        int taskId = createTask(name, description, Integer.parseInt(priority), deadlineTime, deadlineTime, projectId);

        // Return to projects page if creation is successful
        if (taskId > 0) {
            Toast.makeText(getActivity(), "Task created successfully", Toast.LENGTH_SHORT);
            NavDirections action = CreateTaskFragmentDirections.createTaskSuccess(projectId);
            navController.navigate(action);
        } else {
            Toast.makeText(getActivity(), "Failed to create task", Toast.LENGTH_SHORT);
        }
    }

    private int createTask(String name, String desc, int priority, Timestamp endTime, Timestamp deadline, int projectId) {
        int taskId = -1;
        try {
            taskId = Service.tasks.insertTask(new Task(name, desc, priority, 0, projectId, endTime, deadline));
            for (AccountUser assignee : assigneeSelectorModel.getSelectedUsers().getValue()) {
                Service.handles.insertHandle(
                        new Handle(taskId, assignee.getUserID(), new Timestamp(System.currentTimeMillis()))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskId;
    }

    private void openUserSelector() {
        UserSelectionDialog dialog = new UserSelectionDialog();
        dialog.setViewModelOwner(this);
        dialog.show(getChildFragmentManager(), "Create Task");
    }
}