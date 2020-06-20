package com.example.taskoro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;



public class MainActivity extends AppCompatActivity {

    ListView listView;
    // Custom adapter from FirebaseUI library
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        // Whole database -> each individual Task
        final Query query = FirebaseDatabase.getInstance().getReference().child("Tasks");

        // Sets the layout of the ListView items, plugs in the database and the class which defines each Task
        FirebaseListOptions<Tasks> options = new FirebaseListOptions.Builder<Tasks>().setLayout(R.layout.item).setQuery(query, Tasks.class).build();

        /*
            Finds the TextViews from the layout responsible for defining the appearance of each ListView item
            Assigns to each TextView the relevant task information from the database
            Each item in the ListView corresponds to a task
         */
        adapter = new FirebaseListAdapter(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView tskName = v.findViewById(R.id.taskName);
                TextView tskDesc = v.findViewById(R.id.taskDescription);
                TextView tskTime = v.findViewById(R.id.taskTime);
                TextView tskTimeSpent = v.findViewById(R.id.timeSpent);

                Tasks tsk = (Tasks) model;

                tskName.setText("Task name: " + tsk.getTaskName());
                tskDesc.setText("Task Description: " + tsk.getTaskDescription());
                tskTime.setText("Expected Duration: " + tsk.getTaskTime());
                tskTimeSpent.setText("You have spent " + tsk.getTimeSpent() + " on this task");
            }
        };

        // Sets the above adapter to the ListView
        listView.setAdapter(adapter);
        /*
            For the current task that is clicked on:
                Get the Task class
                Send the tasks name, description, time spent and time diff to the Timer activity
                Go to the Timer activity
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tasks tsk = (Tasks) adapterView.getItemAtPosition(i);

                Intent timer = new Intent(MainActivity.this, Timer.class);

                timer.putExtra("taskName", tsk.getTaskName());
                timer.putExtra("timeSpent", tsk.getTimeSpent());
                timer.putExtra("timeDiff", tsk.getTimeDiff());

                startActivity(timer);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    // Goes to the activity for creating new Tasks
    public void newTaskButton(View view) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }
}