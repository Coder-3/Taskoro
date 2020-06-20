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

public class Timer extends AppCompatActivity {
    TextView taskTitle;
    TextView prevTimeSpent;
    Chronometer chronometer;
    Chronometer chronometerTwo;
    String timeSpent;
    String taskName;
    boolean running;
    long timeDiffOne;
    long timeDiffTwo;
    DatabaseReference reference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        taskName = getIntent().getStringExtra("taskName");
        timeSpent = getIntent().getStringExtra("timeSpent");
        timeDiffTwo = getIntent().getLongExtra("timeDiff", 0);

        taskTitle = findViewById(R.id.taskTitle);
        taskTitle.setText(taskName);
        prevTimeSpent = findViewById(R.id.prevTimeSpent);
        prevTimeSpent.setText("You have spent " + timeSpent + " on this task");

        chronometer = findViewById(R.id.chronometer);
        chronometerTwo = findViewById(R.id.chronometerTwo);
        chronometerTwo.setBase(SystemClock.elapsedRealtime() - timeDiffTwo);
    }

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

    public void startChronometer(View view) {
        if(!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - timeDiffOne);
            chronometer.start();
            chronometerTwo.setBase(SystemClock.elapsedRealtime() - timeDiffTwo);
            chronometerTwo.start();
            running = true;
        }
    }

    public void deleteTask(View view) {
        reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(taskName);
        reference.removeValue();
        backToMain(view);
    }

    public void pauseChronometer(View view) {
        if(running) {
            chronometer.stop();
            chronometerTwo.stop();
            timeDiffOne = SystemClock.elapsedRealtime() - chronometer.getBase();
            timeDiffTwo = SystemClock.elapsedRealtime() - chronometerTwo.getBase();
            running = false;
        }
    }

    public void backToMain(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

}