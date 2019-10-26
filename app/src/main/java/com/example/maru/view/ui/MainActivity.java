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
import com.example.maru.view.ui.adapter.SimpleAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private MainViewModel mViewModel;
    RecyclerView recyclerView;
    MeetingJava meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_tb_toolbar);
        setSupportActionBar(toolbar);
        floatingButton();
        launchRecyclerView();
        retriveMeetingFromIntent();

        /*// TODO : create method with adapter and recycler view in MainActivity
        final MainAdapter adapter = new MainAdapter();
        RecyclerView recyclerView = findViewById(R.id.main_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

        mViewModel.getUiModelsLiveData().observe(this, new Observer<List<PropertyUiModel>>() {
            @Override
            public void onChanged(List<PropertyUiModel> propertyUiModels) {
                adapter.submitList(propertyUiModels);
            }
        });*/

        /*// TODO : à déplacer dans CreateMeeting quand on click sur OK pour valider la réunion
        Button insertRandomPropertyButton = findViewById(R.id.create_meeting_bt_valid_meeting);
        insertRandomPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.addNewProperty();
            }
        });*/
    }

    public void retriveMeetingFromIntent(){
        // meeting = (MeetingJava) getIntent().getSerializableExtra("Meeting");
        // Log.d(TAG, "meeting = " +meeting);
        // launchRecyclerView();
    }

    public void launchRecyclerView(){
        meeting = (MeetingJava) getIntent().getSerializableExtra("Meeting");
        recyclerView = findViewById(R.id.main_rv);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), meeting);
        recyclerView.setAdapter(simpleAdapter);
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
