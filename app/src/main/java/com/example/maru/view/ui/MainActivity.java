package com.example.maru.view.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.maru.utility.Builder;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ViewModelFactory;
import com.example.maru.view.ui.adapter.MainAdapter;
import com.example.maru.view.ui.adapter.SimpleAdapter;
import com.example.maru.view.ui.model.PropertyUiModel;
import com.example.maru.view.ui.model.SortingTypeUiModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);
        floatingButton();
        Toolbar toolbar = findViewById(R.id.toolbar_tb_toolbar);
        setSupportActionBar(toolbar);

        final MainAdapter adapter = new MainAdapter();
        configureRecyclerView(adapter);

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

        mViewModel.getUiModelsLiveData().observe(this, new Observer<List<PropertyUiModel>>() {
            @Override
            public void onChanged(List<PropertyUiModel> propertyUiModels) {
                adapter.submitList(propertyUiModels);
            }
        });

        mViewModel.getmSortingTypeUiModelLiveData().observe(this, new Observer<SortingTypeUiModel>() {
            @Override
            public void onChanged(SortingTypeUiModel sortingTypeUiModel) {
                alertDialogChoiceSort(sortingTypeUiModel);
            }
        });
    }

    // Create floating button
    private void floatingButton() {
        // Button for add meeting
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : create new meeting from activity
                // launchCreateMeeting();
                // TODO : create random meeting
                mViewModel.addNewMeeting();
                // mViewModel.launchCreateMeeting(intent);
            }
        });
    }

    // RecyclerView
    private void configureRecyclerView(MainAdapter adapter) {
        RecyclerView recyclerView = findViewById(R.id.main_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // Launch intent for create new meeting
    public void launchCreateMeeting() {
        // Intent intent = new Intent(this.getApplicationContext(), CreateMeetingActivity.class);
        Intent intent = new Intent(this.getApplicationContext(), CreateMeetingActivityJava.class);
        startActivity(intent);
    }

    // Create option menu
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
            mViewModel.displaySortingTypePopup();
        }
        return super.onOptionsItemSelected(item);
    }

    // Alert Dialog Choice Sort
    public void alertDialogChoiceSort(SortingTypeUiModel sortingTypeUiModel) {

        // Setup Alert builder
        final AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle("Choisis le trie que tu souhaites");

        // Setup list of choice
        myPopup.setSingleChoiceItems(sortingTypeUiModel.getNames().toArray(
                new String[sortingTypeUiModel.getNames().size()]),
                sortingTypeUiModel.getSelectedIndex(), (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }));

        // Setup "Valider" button
        myPopup.setPositiveButton("Valider", (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Log.d(TAG, "CHECKED_ITEMS_IN_MENU = " +CHECKED_ITEMS_IN_MENU );
                ListView lw = ((AlertDialog) dialog).getListView();
                Object checkedItemObject = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                Toast.makeText(MainActivity.this.getApplicationContext(), "Tu as choisi " + checkedItemObject, Toast.LENGTH_LONG).show();
                mViewModel.setSortingType((String) checkedItemObject);
            }
        }));

/*

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
*/

        myPopup.setCancelable(false);

        // create and show the alert dialog
        AlertDialog dialog = myPopup.create();
        myPopup.show();
    }
}