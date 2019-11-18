package com.example.maru.view.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
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
import com.example.maru.utility.SortingTypeUiModelManager;
import com.example.maru.view.ViewModelFactory;
import com.example.maru.view.ui.adapter.MainAdapter;
import com.example.maru.view.ui.model.PropertyUiModel;
import com.example.maru.view.ui.model.SortingTypeUiModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.CallbackListener {

    private static final String TAG = "tag";
    private MainViewModel mViewModel;
    final MainAdapter adapter = new MainAdapter(this);
    int mSelectedSortingTypeIndex;
    SortingTypeUiModel mSortingTypeUiModel = SortingTypeUiModelManager.getInstance().getSortingTypeUiModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);

        floatingButton();
        setToolbar();

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

        mViewModel.getSelectedSortingTypeIndexLiveDate().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer newIndex) {
                mSelectedSortingTypeIndex=newIndex;
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_tb_toolbar);
        setSupportActionBar(toolbar);
    }

    // Create floating button
    private void floatingButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCreateMeeting();
                // mViewModel.addNewRandomMeeting();
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
        /*myPopup.setSingleChoiceItems(sortingTypeUiModel.getNames().toArray(
                new String[sortingTypeUiModel.getNames().size()]),
                sortingTypeIndex, (new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // mViewModel.setSortingTypeFromInt(which);
                    }
                }));*/

        // TODO : not use singleton, use ViewModel for trie 18/11/2019
        myPopup.setSingleChoiceItems(sortingTypeUiModel.getListOfSortingType().toArray(
                new CharSequence[sortingTypeUiModel.getListOfSortingType().size()]),
                sortingTypeUiModel.getSelectedIndex(),(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }));

        // Setup "Valider" button
        myPopup.setPositiveButton("Valider", (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lw = ((AlertDialog) dialog).getListView();
                Object checkedItemObject = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                Toast.makeText(MainActivity.this.getApplicationContext(), "Tu as choisi " + checkedItemObject, Toast.LENGTH_LONG).show();
                mViewModel.setSortingType((String) checkedItemObject, sortingTypeUiModel);
            }
        }));

        myPopup.setCancelable(false);

        AlertDialog dialog = myPopup.create();
        myPopup.show();
    }

    @Override
    public void onMeetingClicked(int meetingId) {
        mViewModel.deleteMeeting(meetingId);
    }
}