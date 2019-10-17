package com.example.maru.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.maru.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_tb_toolbar);
        setSupportActionBar(toolbar);
        floatingButton();
    }

    public void launchCreateMeeting() {
        Intent intent = new Intent(this.getApplicationContext(), CreateMeetingActivityJava.class);
        startActivity(intent);
    }

    public void floatingButton(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCreateMeeting();
            }
        });
    }
}
