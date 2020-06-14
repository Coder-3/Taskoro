package com.example.taskoro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.listView);
        final TextAdapter adapter = new TextAdapter();
        final List<Tasks> list = new ArrayList<>();

        adapter.setData(list);
        listView.setAdapter(adapter);


        reference = FirebaseDatabase.getInstance().getReference().child("Tasks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Tasks data = dataSnapshot1.getValue(Tasks.class);
                    list.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        })


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Are you sure you want to delete this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);
                                adapter.setData(list);
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                dialog.show();
            }
        });

//        final Button newTaskButton = findViewById(R.id.newTaskButton);

//        newTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final EditText taskInput = new EditText(MainActivity.this);
//                taskInput.setSingleLine();
//                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Add new task")
//                        .setMessage("What is your task?")
//                        .setView(taskInput)
//                        .setPositiveButton("Add task", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                list.add(taskInput.getText().toString());
//                                adapter.setData(list);
//                            }
//                        })
//                        .setNegativeButton("Cancel", null)
//                        .create();
//                dialog.show();
//            }
//        });

    }

    class TextAdapter extends BaseAdapter {

        List<String> list = new ArrayList<>();
        void setData(List<String> mList) {
            list.clear();
            list.addAll(mList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert inflater != null;
            View rowView = inflater.inflate(R.layout.item, parent, false);
            TextView textView = rowView.findViewById(R.id.taskName);
            textView.setText(list.get(position));

            return rowView;
        }
    }

    public void pomodoro() {

    }

    public void newTaskButton(View view) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }
}