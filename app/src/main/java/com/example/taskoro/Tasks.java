package com.example.taskoro;

public class Tasks {
    private String taskName;
    private String taskDescription;
    private String taskTime;
    private String timeSpent;
    private long timeDiff;

    public Tasks() {
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

    public void setTaskDescription(String taskDescription) { this.taskDescription = taskDescription; }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public long getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(long timeDiff) { this.timeDiff = timeDiff; }

}
