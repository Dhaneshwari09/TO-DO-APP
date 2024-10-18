package com.cscorner.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Database;
import androidx.room.Room;


public class TaskFromActivityy extends Activity {

    private EditText editTextTitle, editTextDescription, editTextDueDate;
    private Spinner spinnerPriority;
    private TaskDatabase db;
    Button buttonSave;
    private Task tasktoedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_from_activityy);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        buttonSave = findViewById(R.id.buttonSave);

        db = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "task_db")
                .allowMainThreadQueries()
                .build();

        Intent intent = getIntent();
        int taskId = intent.getIntExtra("taskId", -1);

        if (taskId != -1) {
            // Fetch task to edit from the database
            tasktoedit = db.taskDao().getTaskById(taskId);

            if (tasktoedit != null) {
                // Populate the fields with the existing task data
                editTextTitle.setText(tasktoedit.getTitle());
                editTextDescription.setText(tasktoedit.getDescription());
                editTextDueDate.setText(tasktoedit.getDueDate());
                 spinnerPriority.setSelection(tasktoedit.getPriority() - 1);
            }
        }

        buttonSave.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString();
            String dueDate = editTextDueDate.getText().toString();
            int priority = spinnerPriority.getSelectedItemPosition() + 1;

            if (!TextUtils.isEmpty(title)) {
                if (tasktoedit == null) {
                    Task task = new Task(title, description, dueDate, priority, true);
                    db.taskDao().insert(task);
                } else {
                    // Update the existing task
                    tasktoedit.setTitle(title);
                    tasktoedit.setDescription(description);
                    tasktoedit.setDueDate(dueDate);
                   tasktoedit.setPriority(priority);
                    db.taskDao().update(tasktoedit); // Update task in database
                }

                finish(); // Close the activity after saving

            } else {
                Toast.makeText(TaskFromActivityy.this, "Title is required", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

