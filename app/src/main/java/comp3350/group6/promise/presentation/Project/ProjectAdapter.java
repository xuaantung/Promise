package comp3350.group6.promise.presentation.Project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.group6.promise.R;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Project;

/*
    This class is used to map lists of Project objects to views within the interface.
    Reference for Class: https://www.geeksforgeeks.org/cardview-using-recyclerview-in-android-with-example/
*/

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private List<Project> projectsList;
    private ViewHolder.OnProjectClickListener onProjectClickListener;

    public ProjectAdapter(List<Project> projectsList, ViewHolder.OnProjectClickListener onProjectClickListener){
        this.projectsList = projectsList;
        this.onProjectClickListener = onProjectClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item, parent, false);
        return new ViewHolder(view, onProjectClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Project project = projectsList.get(position);

        if(project != null){
            int numUsers = 0;
            int numTasks = 0;

            try {
                numUsers = Service.accesses.getUsers(project.getProjectID()).size();
                numTasks = Service.tasks.getTasksByProjectId(project.getProjectID(),1).size();
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.nameView.setText(project.getProjectName());
            holder.descriptionView.setText(project.getStatement());
            holder.imageView.setImageResource(R.drawable.astro);
            holder.memberCountView.setText(Integer.toString(numUsers));
            holder.taskCountView.setText(Integer.toString(numTasks));
        }
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout container;
        private TextView nameView;
        private TextView descriptionView;
        private TextView memberCountView;
        private TextView taskCountView;
        private ImageView imageView;

        private OnProjectClickListener listener;

        public ViewHolder(View itemView, OnProjectClickListener onProjectClickListener){
            super(itemView);

            container = itemView.findViewById(R.id.project_container);
            nameView = itemView.findViewById(R.id.project_name);
            descriptionView = itemView.findViewById(R.id.project_description);
            memberCountView = itemView.findViewById(R.id.project_member_count);
            taskCountView = itemView.findViewById(R.id.project_task_count);
            imageView = itemView.findViewById(R.id.project_image);

            listener = onProjectClickListener;

            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onProjectClick(getAbsoluteAdapterPosition());
        }

        public interface OnProjectClickListener {
            void onProjectClick(int position);
        }
    }
}
