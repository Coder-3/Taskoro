package com.example.taskoro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final String taskNames[];
//        String taskDescriptions[];
//        String taskTimes[];

//        reference = FirebaseDatabase.getInstance().getReference().child("Tasks");
        listView = findViewById(R.id.listView);
        Query query = FirebaseDatabase.getInstance().getReference().child("Tasks");


        FirebaseListOptions<Tasks> options = new FirebaseListOptions.Builder<Tasks>().setLayout(R.layout.item).setQuery(query, Tasks.class).build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView tskName = v.findViewById(R.id.taskName);
                TextView tskDesc = v.findViewById(R.id.taskDescription);
                TextView tskTime = v.findViewById(R.id.taskTime);

                Tasks tsk = (Tasks) model;

                tskName.setText("Task name: " + tsk.getTaskName());
                tskDesc.setText("Task Description: " + tsk.getTaskDescription());
                tskTime.setText("Task Time: " + tsk.getTaskTime());
            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Clicked?", "clicked");
                Intent deleteTask = new Intent(MainActivity.this, DeleteTask.class);
                Tasks t = (Tasks) adapterView.getItemAtPosition(i);
                deleteTask.putExtra("taskName", t.getTaskName());
                deleteTask.putExtra("taskDescription", t.getTaskDescription());
                deleteTask.putExtra("taskTime", t.getTaskTime());
                startActivity(deleteTask);
            }
        });


//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

//        MyAdapter adapter = new MyAdapter(this, taskNames, taskDescriptions, taskTimes);

//        ArrayList<Tasks> testArrayList = new ArrayList<>();
//        String testThree = testArrayList.get(1).getTaskName();
//        Log.i("arrayList values", testThree);



//        listView.setAdapter(arrayAdapter);
//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                String value = dataSnapshot.getValue(Tasks.class).toString();
//                arrayList.add(value);
//                arrayAdapter.notifyDataSetChanged();
//
////                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
////                    Tasks data = dataSnapshot1.getValue(Tasks.class);
////                    String stringData = data.toString();
////                    arrayList.add(stringData);
////                    arrayAdapter.notifyDataSetChanged();
////                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
//                    Tasks data = dataSnapshot1.getValue(Tasks.class);
//                    list.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        })
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

//    class MyAdapter extends ArrayAdapter<String> {
//        Context context;
//        String taskNames[];
//        String taskDescriptions[];
//        String taskTimes[];
//
//        MyAdapter(Context c, String taskName[], String taskDescription[], String taskTime[]) {
//            super(c, R.layout.item, R.id.taskName, taskName);
//            this.context = c;
//            this.taskDescriptions = taskDescription;
//            this.taskTimes = taskTime;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View item = layoutInflater.inflate(R.layout.item, parent, false);
//            TextView theTaskNames = item.findViewById(R.id.taskName);
//            TextView theTaskDescriptions = item.findViewById(R.id.taskDescription);
//            TextView theTaskTimes = item.findViewById(R.id.taskTime);
//
//            return item;
//        }
//    }
//
//    private void fetchData(DataSnapshot dataSnapshot)
//    {
//        for (DataSnapshot ds : dataSnapshot.getChildren())
//        {
//            String taskName=ds.getValue(Tasks.class).getTaskName();
//            tasks.add(name);
//        }
//    }

//    class TextAdapter extends BaseAdapter {
//
//        List<String> list = new ArrayList<>();
//        void setData(List<String> mList) {
//            list.clear();
//            list.addAll(mList);
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            assert inflater != null;
//            View rowView = inflater.inflate(R.layout.item, parent, false);
//            TextView textView = rowView.findViewById(R.id.taskName);
//            textView.setText(list.get(position));
//
//            return rowView;
//        }
//    }

    public void pomodoro() {
        reference.removeValue();
    }

    public void newTaskButton(View view) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }
}