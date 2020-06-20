package com.example.taskoro;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
    The purpose of this activity is to set a timer for each task
    This allows the user to keep track of how long they have spent on each task
    The time spent is stored in the Firebase realtime database
    The current time spent is added to the previous time spent
    The total time spent is then showed in the main activity
 */
public class Timer extends AppCompatActivity {
    // Used to show task name at the top of the activity
    TextView taskTitle;
    // Used to show the total time previously spent on the task under the task name
    TextView prevTimeSpent;
    // Chronometer for the CURRENT timer session
    Chronometer chronometer;
    // Chronometer for the current + previous timer sessions
    Chronometer chronometerTwo;
    // Total time previously spent on the task
    String timeSpent;
    // Name of current task
    String taskName;
    // Checks if the chronometers are running
    boolean running;
    // How long has the chronometer for the current session been running
    long timeDiffOne;
    // Total time chronometer has been running for this task
    long timeDiffTwo;
    DatabaseReference reference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // The database values for the current task
        taskName = getIntent().getStringExtra("taskName");
        timeSpent = getIntent().getStringExtra("timeSpent");
        timeDiffTwo = getIntent().getLongExtra("timeDiff", 0);

        // Using the time spent and task name for the current task
        // Displaying them in the TextViews
        taskTitle = findViewById(R.id.taskTitle);
        taskTitle.setText(taskName);
        prevTimeSpent = findViewById(R.id.prevTimeSpent);
        prevTimeSpent.setText("You have spent " + timeSpent + " on this task");

        // chronometer starts from 00:00
        // chronometerTwo starts from the total time previously spent on this task
        chronometer = findViewById(R.id.chronometer);
        chronometerTwo = findViewById(R.id.chronometerTwo);
        chronometerTwo.setBase(SystemClock.elapsedRealtime() - timeDiffTwo);
    }

    /*
        Stops the chronometers
        Saves how long both chronometers have been running
        Saves the total time spent on the task to the database
     */
    public void saveTimer(View view) {
        chronometer.stop();
        chronometerTwo.stop();
        running = false;
        String totalTimeSpent = chronometerTwo.getText().toString();
        timeDiffTwo = SystemClock.elapsedRealtime() - chronometerTwo.getBase();
        timeDiffOne = SystemClock.elapsedRealtime() - chronometer.getBase();
        reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(taskName);
        reference.child("timeSpent").setValue(totalTimeSpent);
        reference.child("timeDiff").setValue(timeDiffTwo);
    }

    /*
        Starts chronometer from previous total time spent in this current sessions
        Starts chronometerTwo from the previous total time spent on this task
        Sets running to true
     */
    public void startChronometer(View view) {
        if(!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - timeDiffOne);
            chronometer.start();
            chronometerTwo.setBase(SystemClock.elapsedRealtime() - timeDiffTwo);
            chronometerTwo.start();
            running = true;
        }
    }

    /*
        Deletes the current task from the database
        This is done by using the task name as the key for this current task
        Calls backToMain() to return to MainActivity
     */
    public void deleteTask(View view) {
        reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(taskName);
        reference.removeValue();
        backToMain(view);
    }

    /*
        Pauses both chronometers
        Save how long chronometer has been running this session
        Save the total time chronometerTwo has been running for this task
        Sets running to false
     */
    public void pauseChronometer(View view) {
        if(running) {
            chronometer.stop();
            chronometerTwo.stop();
            timeDiffOne = SystemClock.elapsedRealtime() - chronometer.getBase();
            timeDiffTwo = SystemClock.elapsedRealtime() - chronometerTwo.getBase();
            running = false;
        }
    }

    // Returns to main activity
    public void backToMain(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

}