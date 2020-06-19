package com.example.taskoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTask extends AppCompatActivity {

    EditText taskName, taskDescription, taskTime;
    Button addTaskButton;
    DatabaseReference reference;
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
        reference = FirebaseDatabase.getInstance().getReference().child("Tasks");

        addTaskButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Todo: Add validation
                String name = taskName.getText().toString().trim();
                String description = taskDescription.getText().toString().trim();
                String time = taskTime.getText().toString().trim();
                tasks.setTaskName(name);
                tasks.setTaskDescription(description);
                tasks.setTaskTime(time);
                reference.push().setValue(tasks);

                startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    public void cancelNewTask(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}