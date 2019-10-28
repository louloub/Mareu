package com.example.maru.view.ui;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.service.model.Meeting;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.adapter.SimpleAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private MainViewModel mViewModel;
    RecyclerView recyclerView;
    private ArrayList<MeetingJava> listOfMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_rv);
        Toolbar toolbar = findViewById(R.id.toolbar_tb_toolbar);
        setSupportActionBar(toolbar);
        floatingButton();
        launcher();
    }

    public void launcher () {
        MeetingManager.getInstance();
        listOfMeeting =  MeetingManager.getMeeting();
        if (listOfMeeting != null)
        {
            // recyclerView.setHasFixedSize(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), listOfMeeting);
            recyclerView.setAdapter(simpleAdapter);
        } else {
            Log.d(TAG, "meeting listOfMeeting is null ");
        }
    }

    public void retriveMeetingFromIntent(){
        // meeting = (MeetingJava) getIntent().getSerializableExtra("Meeting");
        // Log.d(TAG, "meeting = " +meeting);
        // launchRecyclerView();
    }

    public void launchRecyclerView(){
        // recyclerView = findViewById(R.id.main_rv);
        /*recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), listMeeting);*/

        // launchRecyclerView(simpleAdapter);
        // MeetingJava meeting = (MeetingJava) getIntent().getSerializableExtra("Meeting");

        // listMeeting.add(meeting);
        /*if (meeting != null) {
            String test = meeting.getSubject();
            listMeeting.add(meeting);
            recyclerView.setAdapter(simpleAdapter);
        } else {
            Log.d(TAG, "meeting is null ");
        }*/
    }

    public void launchCreateMeeting() {
        // Intent intent = new Intent(this.getApplicationContext(), CreateMeetingActivity.class);
        Intent intent = new Intent(this.getApplicationContext(), CreateMeetingActivityJava.class);
        startActivity(intent);
    }

    public void floatingButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCreateMeeting();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void sortData(boolean asc)
    {
        //SORT ARRAY ASCENDING AND DESCENDING
        if (asc)
        {
            // return Integer.valueOf(lhs.getDistance()).compareTo(rhs.getDistance());

            // Collections.sort(listOfMeeting,Comparator.comparing());
            // Collections.sort(listOfMeeting,Comparator);
            // Collections.sort(ArrayList<MeetingJava> listOfMeeting);
        }
        else
        {
            // Collections.reverse(spacecrafts);
        }

        //ADAPTER
        /*adapter = new MyAdapter(this, spacecrafts);
        rv.setAdapter(adapter);*/

    }

}
