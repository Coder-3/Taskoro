package com.example.taskoro;

public class Tasks {
    private String taskName;
    private String taskDescription;
    private String taskTime;

    public Tasks() {
    }

    public Tasks(String taskName, String taskDescription, String taskTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskTime = taskTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
}
