package com.cscorner.todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tasks")
public class Task implements Serializable {

        @PrimaryKey(autoGenerate = true)
        private int id;

        private String title;
        private String description;
        private String dueDate;
        private int priority; // 1-High, 2-Medium, 3-Low
        private boolean isCompleted;

        // Constructors, getters and setters
        public Task( String title, String description,String dueDate, int priority, boolean isCompleted) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.priority = priority;
            this.isCompleted = isCompleted;
        }

        // Getters and setters...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
}

