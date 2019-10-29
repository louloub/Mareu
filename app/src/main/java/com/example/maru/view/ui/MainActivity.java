package com.example.maru.view.ui;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    private boolean ascending = true;

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

    // Launch intent for create new meeting
    public void launchCreateMeeting() {
        // Intent intent = new Intent(this.getApplicationContext(), CreateMeetingActivity.class);
        Intent intent = new Intent(this.getApplicationContext(), CreateMeetingActivityJava.class);
        startActivity(intent);
    }

    // Button for add meeting
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

    // Item selected in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.toolbar_bt_sort_meeting) {
            alertDialogChoiceSort();
            /*if (ascending) {
                sortRoom(ascending);
                Toast toastCrescent = Toast.makeText(MainActivity.this, "Trie croissant", Toast.LENGTH_SHORT);
                toastCrescent.show();
                ascending =! ascending;
            } else if (!ascending) {
                sortRoom(ascending);
                Toast toastDecrease = Toast.makeText(MainActivity.this, "Trie décroissant", Toast.LENGTH_SHORT);
                toastDecrease.show();
                ascending =! ascending;
            }*/
        } else {
            // ELSE
        }
        return super.onOptionsItemSelected(item);
    }

    public void alertDialogChoiceSort(){

        int checkedItems = 0;

        SharedPreferences settings = getSharedPreferences("MySortChoice", Context.MODE_PRIVATE);
        String sortChoiceSharedPreferences = settings.getString("MySortChoice",null);

        assert sortChoiceSharedPreferences != null;

        if (sortChoiceSharedPreferences==null) {
            checkedItems = 0;
        } else {
            if(sortChoiceSharedPreferences.equals("Croissant salle"))
            { checkedItems = 0;}
            else if (sortChoiceSharedPreferences.equals("Decroissant salle"))
            { checkedItems = 1;}
            else if (sortChoiceSharedPreferences.equals("Croissant date")) {
                checkedItems = 2;
            } else if (sortChoiceSharedPreferences.equals("Decroissant date")) {
                checkedItems = 3;
            } else { checkedItems = 0; }
        }

        // Setup Alert builder
        final AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle("Choisis le trie que tu souhaites");
        // Ddd a radio button list
        String[] sortChoice = {"Croissant salle", "Decroissant salle", "Croissant date", "Decroissant date"};

        myPopup.setSingleChoiceItems(sortChoice,checkedItems, (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }));

        myPopup.setPositiveButton("Valider", (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lw = ((AlertDialog) dialog).getListView();
                Object checkedItemObject = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                Toast.makeText(MainActivity.this.getApplicationContext(), "Tu as choisi " + checkedItemObject, Toast.LENGTH_LONG).show();

                if (checkedItemObject.toString()=="Croissant salle") {
                    sortRoom(ascending);
                }

                // FOR RELOAD CONTENT WHEN CITY IS CHOICE
                // finish();
                // startActivity(getIntent());
            }
        }));

        myPopup.setCancelable(false);

        // create and show the alert dialog
        AlertDialog dialog = myPopup.create();
        myPopup.show();
    }

    // Comparator to sort meeting list in order of room
    public static Comparator<MeetingJava> MeetingComparator = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e1.getRoom() - e2.getRoom());
        }
    };

    // Sort room
    private void sortRoom(boolean asc)
    {
        //SORT ARRAY ASCENDING AND DESCENDING
        if (asc)
        {
            Collections.sort(listOfMeeting,MeetingComparator);
        }
        else
        {
            Collections.reverse(listOfMeeting);
        }
        //ADAPTER
        SimpleAdapter adapter = new SimpleAdapter(this, listOfMeeting);
        recyclerView.setAdapter(adapter);
    }
}