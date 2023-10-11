package comp3350.group6.promise.presentation.Task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.group6.promise.R;
import comp3350.group6.promise.objects.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> taskList;
    private OnTaskClickListener onTaskClickListener;
    private OnTaskLongClickListener onTaskLongClickListener;

    public TaskAdapter(Context context, List<Task> taskList, OnTaskClickListener onTaskClickListener, OnTaskLongClickListener onTaskLongClickListener) {
        this.taskList = taskList;
        this.onTaskClickListener = onTaskClickListener;
        this.onTaskLongClickListener = onTaskLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        return new ViewHolder(view, this.onTaskClickListener, this.onTaskLongClickListener);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        Task task = taskList.get(position);
        viewHolder.titleView.setText(task.getTitle());
        viewHolder.descriptionView.setText(task.getDescription());
    }

    @Override
    public int getItemCount() {
        if (taskList != null) {
            return taskList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private LinearLayout container;
        private TextView titleView;
        private TextView descriptionView;
        private OnTaskClickListener clickListener;
        private OnTaskLongClickListener longClickListener;

        public ViewHolder(View itemView, OnTaskClickListener onTaskClickListener, OnTaskLongClickListener onTaskLongClickListener) { // constructor
            super(itemView);

            this.container = itemView.findViewById(R.id.task_container);
            this.titleView = itemView.findViewById(R.id.task_title);
            this.descriptionView = itemView.findViewById(R.id.task_description);

            this.clickListener = onTaskClickListener;
            this.longClickListener = onTaskLongClickListener;

            container.setOnClickListener(this);
            container.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onTaskClick(getAbsoluteAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            longClickListener.onLongTaskClick(getAbsoluteAdapterPosition());
            return true;
        }

    }

    public interface OnTaskClickListener {
        void onTaskClick(int position);
    }

    public interface OnTaskLongClickListener {
        void onLongTaskClick(int position);
    }
}
