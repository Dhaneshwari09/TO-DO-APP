package com.cscorner.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class recyclerview extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {


    private TaskDatabase db;
    private TaskAdapter adapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview2);

        db = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "task_db")

                .allowMainThreadQueries()
                .build();
        tasks = db.taskDao().getAllTasks();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(tasks, this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(recyclerview.this, TaskFromActivityy.class);
            startActivity(intent);
        });

    }

    @Override
    public void onTaskClick(Task task) {
        // Start TaskFormActivity for editing the task
        Intent intent = new Intent(recyclerview.this, TaskFromActivityy.class);

        // Pass task details to TaskFormActivity using intent extras
        intent.putExtra("taskId", task.getId());
        intent.putExtra("taskTitle", task.getTitle());
        intent.putExtra("taskDescription", task.getDescription());
        intent.putExtra("taskDueDate", task.getDueDate());
        intent.putExtra("taskPriority", task.getPriority());
        intent.putExtra("taskIsCompleted", task.isCompleted());

        startActivity(intent);

    }

    // method for delet task
    @Override
    public void onTaskDelete(Task task) {

        if (task == null) {
            Log.e("TaskError", "Task is null!");
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.taskDao().delete(task); // Task ko delete karna
                        tasks.remove(task); // List se task ko remove karna
                        adapter.notifyDataSetChanged(); // Adapter ko refresh karna
                    }
                })
                .setNegativeButton("No", null)
                .show();

}


        @Override
        public void onTaskCompletionToggle (@NonNull Task task){
            task.setCompleted(!task.isCompleted());
            db.taskDao().update(task);
            adapter.notifyDataSetChanged();
    }
//
        @Override
        protected void onResume () {
            super.onResume();
            tasks = db.taskDao().getAllTasks();
            adapter.updateTasks(tasks);
        }
    }

