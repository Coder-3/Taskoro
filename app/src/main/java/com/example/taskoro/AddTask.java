package com.example.taskoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTask extends AppCompatActivity {

    // EditTexts where the user enters the information for the current task they are creating
    EditText taskName, taskDescription, taskTime;
    // Button for adding the task to the Firebase realtime database
    Button addTaskButton;
    DatabaseReference reference;
    // Class responsible for the structure of each class
    Tasks tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskName = findViewById(R.id.addTaskName);
        taskDescription = findViewById(R.id.addTaskDescription);
        taskTime = findViewById(R.id.addTaskTime);
        addTaskButton = findViewById(R.id.addTaskButton);

        tasks = new Tasks();
        // Whole database -> each individual Task
        reference = FirebaseDatabase.getInstance().getReference().child("Tasks");

        /*
            When the user clicks the button to Add Task
                Get the text the user entered from the EditTexts
                Set 00:00 time spent and 0 time diff because no time has been spent on this task yet
                Add all those values to tasks
                Set the key for this task as the task name
                Send task to the Firebase realtime database where a new task is made containing the above info
                Returns to MainActivity
         */
        addTaskButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = taskName.getText().toString().trim();
                String description = taskDescription.getText().toString().trim();
                String time = taskTime.getText().toString().trim();
                String timeSpent = "00:00";
                long timeDiff = 0;

                tasks.setTaskName(name);
                tasks.setTaskDescription(description);
                tasks.setTaskTime(time);
                tasks.setTimeSpent(timeSpent);
                tasks.setTimeDiff(timeDiff);
                reference.child(name).setValue(tasks);

                startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    // Returns to MainActivity
    public void cancelNewTask(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}