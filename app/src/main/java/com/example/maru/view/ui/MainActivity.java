package com.example.maru.view.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.maru.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_tb_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                launchCreateMeeting();
            }
        });
    }

    public void launchCreateMeeting(){
        Intent intent = new Intent(this.getApplicationContext(), CreateMeetingActivity.class);
        startActivity(intent);
    }

    // TODO : use ThreeTenABP, for google shearch tape : java8time etc .. (it's compatible)
    // TODO : LiveData check le git de nino
}
