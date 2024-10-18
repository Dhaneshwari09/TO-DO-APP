package com.cscorner.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private TaskDatabase db;
    private List<Task> tasks;
    private OnTaskClickListener listener;
    private Context context;

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
        void onTaskDelete(Task task);
        void onTaskCompletionToggle(Task task);
    }

    public TaskAdapter(List<Task> tasks, OnTaskClickListener listener) {
        this.db=db;
        this.tasks = tasks;
        this.listener = listener;
        this.context=context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textViewTitle.setText(task.getTitle());
        holder.textViewDueDate.setText(task.getDueDate());

        holder.itemView.setOnClickListener(v -> listener.onTaskClick(task));
        holder.buttonDelete.setOnClickListener(v -> listener.onTaskDelete(task));

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDueDate;
        ImageButton buttonDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTaskTitle);
            textViewDueDate = itemView.findViewById(R.id.textViewTaskDueDate);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
 }
}
    public void updateTasks(List<Task> newTasks) {
        this.tasks = newTasks;
        notifyDataSetChanged();  // Notify the adapter that the data has changed
    }

}


