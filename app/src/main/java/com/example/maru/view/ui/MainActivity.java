package com.example.maru.view.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ViewModelFactory;
import com.example.maru.view.ui.adapter.MainAdapter;
import com.example.maru.view.ui.adapter.SimpleAdapter;
import com.example.maru.view.ui.model.PropertyUiModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;

    /*// RecyclerView recyclerView;
    int checkedItems = 0;
    private ArrayList<MeetingJava> listOfMeeting;
    private boolean ascendingRoom = true;
    private boolean ascendingDate = true;

    private static final String TAG = "TAG";

    // Comparator to sort meeting list in order of room
    public static Comparator<MeetingJava> RoomComparator = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e1.getRoom() - e2.getRoom());
        }
    };

    // Comparator to sort meeting list in order of date
    public static Comparator<MeetingJava> DateComparator = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e1.getDate().compareTo(e2.getDate()));
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO : start MVVM test

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
        });

        // TODO : end MVVM test

        /*recyclerView = findViewById(R.id.main_rv);
        Toolbar toolbar = findViewById(R.id.toolbar_tb_toolbar);
        setSupportActionBar(toolbar);
        floatingButton();
        testListOfMeetingInSingleton();*/
    }

    /*// Test list of meeting in singleton
    public void testListOfMeetingInSingleton() {
        MeetingManager.getInstance();
        listOfMeeting = MeetingManager.getMeeting();
        if (!listOfMeeting.isEmpty()) {
            // recyclerView.setHasFixedSize(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), listOfMeeting);
            recyclerView.setAdapter(simpleAdapter);
        } else {
            Toast.makeText(MainActivity.this.getApplicationContext(), "La liste de réunion est vide", Toast.LENGTH_LONG).show();
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

    // Create menu
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
        } else {
            // ELSE
        }
        return super.onOptionsItemSelected(item);
    }

    // Alert Dialog Choice Sort
    public void alertDialogChoiceSort() {

        // Setup Alert builder
        final AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle("Choisis le trie que tu souhaites");

        // Ddd a radio button list
        String[] sortChoice = {"Croissant salle", "Decroissant salle", "Croissant date", "Decroissant date"};

        // Setup list of choice
        myPopup.setSingleChoiceItems(sortChoice, checkedItems, (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }));

        // Setup button VALIDER
        myPopup.setPositiveButton("Valider", (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lw = ((AlertDialog) dialog).getListView();
                Object checkedItemObject = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                Toast.makeText(MainActivity.this.getApplicationContext(), "Tu as choisi " + checkedItemObject, Toast.LENGTH_LONG).show();

                if (checkedItemObject.toString().equals("Croissant salle")) {
                    sortRoom(ascendingRoom);
                    Toast toastCrescentRoom = Toast.makeText(MainActivity.this, "Trie croissant salle", Toast.LENGTH_SHORT);
                    toastCrescentRoom.show();
                    ascendingRoom = !ascendingRoom;
                    checkedItems = 0;
                } else if (checkedItemObject.toString().equals("Decroissant salle")) {
                    sortRoom(ascendingRoom);
                    Toast toastDecreaseRoom = Toast.makeText(MainActivity.this, "Trie décroissant salle", Toast.LENGTH_SHORT);
                    toastDecreaseRoom.show();
                    ascendingRoom = !ascendingRoom;
                    checkedItems = 1;
                } else if (checkedItemObject.toString().equals("Croissant date")) {
                    sortDate(ascendingDate);
                    Toast toastCrescentDate = Toast.makeText(MainActivity.this, "Trie croissant date", Toast.LENGTH_SHORT);
                    toastCrescentDate.show();
                    ascendingDate = !ascendingDate;
                    checkedItems = 2;
                } else if (checkedItemObject.toString().equals("Decroissant date")) {
                    sortDate(ascendingDate);
                    Toast toastDecreaseDate = Toast.makeText(MainActivity.this, "Trie decroissant date", Toast.LENGTH_SHORT);
                    toastDecreaseDate.show();
                    ascendingDate = !ascendingDate;
                    checkedItems = 3;
                }
            }
        }));

        myPopup.setCancelable(false);

        // create and show the alert dialog
        AlertDialog dialog = myPopup.create();
        myPopup.show();
    }

    // Sort room Method
    private void sortRoom(boolean asc) {
        //SORT ARRAY ASCENDING AND DESCENDING
        if (asc) {
            Collections.sort(listOfMeeting, RoomComparator);
        } else {
            Collections.reverse(listOfMeeting);
        }
        //ADAPTER
        SimpleAdapter adapter = new SimpleAdapter(this, listOfMeeting);
        recyclerView.setAdapter(adapter);
    }

    // Sort date Method
    private void sortDate(boolean asc) {
        // SORT ARRAY ASCENDING AND DESCENDING
        if (asc) {
            Collections.sort(listOfMeeting, DateComparator);
        } else {
            Collections.reverse(listOfMeeting);
        }
        //ADAPTER
        SimpleAdapter adapter = new SimpleAdapter(this, listOfMeeting);
        recyclerView.setAdapter(adapter);
    }*/
}