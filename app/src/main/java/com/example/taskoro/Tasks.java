package com.example.taskoro;

/*
    The structure of a task for adding a new task and getting a task from the Firebase realtime database
    Each task has
       Name of the task
       Description of the task
       The  amount of time the user thinks they will spend on the task
       The total time the user has previously spent on the task (initially 00:00)
       The time diff for use by the chronometers in the Timer class (initially 0)
 */
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
