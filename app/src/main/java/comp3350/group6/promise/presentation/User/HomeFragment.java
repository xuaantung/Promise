package comp3350.group6.promise.presentation.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import comp3350.group6.promise.R;
import comp3350.group6.promise.application.CurrentSession;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Project;
import comp3350.group6.promise.presentation.Project.ProjectAdapter;

public class HomeFragment extends Fragment implements ProjectAdapter.ViewHolder.OnProjectClickListener {

    private List<Project> projects;
    private ProjectAdapter projectAdapter;

    private RecyclerView projectRecyclerView;
    private FloatingActionButton fab;

    private NavController navController;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Get content data

        projects = Service.accesses.getProjects(CurrentSession.getAccount().getUserID());

        // Get views from layout

        navController = Navigation.findNavController(view);

        projectRecyclerView = view.findViewById(R.id.projectRecyclerView);
        fab = getActivity().findViewById(R.id.fab);

        // Update layout content

        projectAdapter = new ProjectAdapter(projects, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        projectRecyclerView.setLayoutManager(linearLayoutManager);
        projectRecyclerView.setAdapter(projectAdapter);

        // Update layout behaviours

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View fab) {
                NavDirections action = HomeFragmentDirections.createProject();
                navController.navigate(action);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh project list
        projectAdapter.notifyDataSetChanged();
    }

    @Override
    public void onProjectClick(int position) {
        int projectId = projects.get(position).getProjectID();
        NavDirections action = HomeFragmentDirections.selectProject(projectId);
        navController.navigate(action);
    }
}