package com.example.maru.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private MainViewModel mViewModel;
    RecyclerView recyclerView;
    private ArrayList<MeetingJava> listMeeting;

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
        ArrayList<MeetingJava> list =  MeetingManager.getMeeting();
        if (list != null)
        {
            recyclerView.setHasFixedSize(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), list);
            recyclerView.setAdapter(simpleAdapter);
        } else {
            Log.d(TAG, "meeting list is null ");
        }

        /*
        MeetingJava meeting = (MeetingJava) getIntent().getSerializableExtra("Meeting");
        listMeeting = new ArrayList<>();
        if (meeting != null) {
            if (listMeeting.isEmpty()) {
                listMeeting = new ArrayList<>();
            }
            listMeeting.add(meeting);
            recyclerView.setHasFixedSize(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), listMeeting);
            recyclerView.setAdapter(simpleAdapter);
        } else {
            Log.d(TAG, "meeting is null ");
        }*/
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
}
