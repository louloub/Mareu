package com.example.mareu.view.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.view.ViewModelFactory;
import com.example.mareu.view.ui.adapter.MainAdapter;
import com.example.mareu.view.ui.model.RoomFilterTypeUiModel;
import com.example.mareu.view.ui.model.MeetingUiModel;
import com.example.mareu.view.ui.model.SortingTypeUiModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.CallbackListener {

    private final MainAdapter adapter = new MainAdapter(this);
    private int mSelectedSortingTypeIndex;
    private int mSelectedFilterTypeIndex;
    private SortingTypeUiModel mSortingTypeUiModel;
    private RoomFilterTypeUiModel mRoomFilterTypeUiModel;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);

        floatingButton();
        setToolbar();

        configureRecyclerView(adapter);

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

        mViewModel.getMeetingUiModelsLiveData().observe(this, new Observer<List<MeetingUiModel>>() {
            @Override
            public void onChanged(List<MeetingUiModel> meetingListUiModel) {
                adapter.submitList(meetingListUiModel);
            }
        });

        mViewModel.getSortingTypeUiModelLiveData().observe(this, new Observer<SortingTypeUiModel>() {
            @Override
            public void onChanged(SortingTypeUiModel sortingTypeUiModel) {
                mSortingTypeUiModel = sortingTypeUiModel;
                alertDialogChoiceSort(sortingTypeUiModel);
            }
        });

        mViewModel.getSelectedSortingTypeIndexLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer newSortingTypeIndex) {
                mSelectedSortingTypeIndex = newSortingTypeIndex;
            }
        });

        mViewModel.getSelectedFilterTypeIndexLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer newFilterTypeIndex) {
                mSelectedFilterTypeIndex = newFilterTypeIndex;
            }
        });

        mViewModel.getFilterTypeUiModelLiveData().observe(this, new Observer<RoomFilterTypeUiModel>() {
            @Override
            public void onChanged(RoomFilterTypeUiModel roomFilterTypeUiModel) {
                mRoomFilterTypeUiModel = roomFilterTypeUiModel;
                alertDialogChoiceRoomFilter(roomFilterTypeUiModel);
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
    private void launchCreateMeeting() {
        Intent intent = new Intent(this.getApplicationContext(), CreateMeetingActivity.class);
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
        } else if (item.getItemId() == R.id.toolbar_bt_filter_room_meeting) {
            mViewModel.displayFilterRoomPopup();
        } else if (item.getItemId() == R.id.toolbar_bt_filter_date_meeting) {
            alertDialogChoiceDateFilter();
        }
        return super.onOptionsItemSelected(item);
    }

    // Alert Dialog Choice Sort
    private void alertDialogChoiceSort(SortingTypeUiModel sortingTypeUiModel) {

        // Setup Alert builder
        final AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle("Choisis le trie que tu souhaites");

        myPopup.setSingleChoiceItems(sortingTypeUiModel.getListOfSortingType().toArray(
                new CharSequence[sortingTypeUiModel.getListOfSortingType().size()]),
                sortingTypeUiModel.getSelectedIndex(), (new DialogInterface.OnClickListener() {
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
                Toast.makeText(
                        MainActivity.this.getApplicationContext(),
                        "Tu as choisi " + checkedItemObject,
                        Toast.LENGTH_LONG).show();

                mViewModel.setSortingType((String) checkedItemObject, mSortingTypeUiModel);
            }
        }));

        myPopup.setCancelable(false);

        AlertDialog dialog = myPopup.create();
        myPopup.show();
    }

    // Alert Dialog Room Filter
    private void alertDialogChoiceRoomFilter(RoomFilterTypeUiModel roomFilterTypeUiModel) {

        // Setup Alert builder
        final AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle("Choisis la salle à filtrer");

        myPopup.setSingleChoiceItems(roomFilterTypeUiModel.getListOFilterType().toArray(
                new CharSequence[roomFilterTypeUiModel.getListOFilterType().size()]),
                roomFilterTypeUiModel.getSelectedIndex(), (new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }));

        // Setup validate button
        myPopup.setPositiveButton("Valider", (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lw = ((AlertDialog) dialog).getListView();
                Object checkedItemObject = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                Toast.makeText(
                        MainActivity.this.getApplicationContext(),
                        "Tu as choisi d'afficher les réunions : " + checkedItemObject,
                        Toast.LENGTH_LONG).show();
                mViewModel.setRoomFilterType((String) checkedItemObject, mRoomFilterTypeUiModel);
            }
        }));

        myPopup.setCancelable(false);

        AlertDialog dialog = myPopup.create();
        myPopup.show();
    }

    // Alert Dialog Date Filter
    private void alertDialogChoiceDateFilter() {

        // Setup Alert builder
        final AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle("Choisis la date à filtrer");
        myPopup.setMessage("Exemple : 2019-12-01");

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        myPopup.setView(input);

        // Setup validate button
        myPopup.setPositiveButton("Valider", (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SpannableString contentText = new SpannableString(input.getText());
                String dateForFilter = contentText.toString();

                if (dateForFilter.isEmpty()) {
                    Toast.makeText(
                            MainActivity.this.getApplicationContext(),
                            "Tu as choisi d'afficher toutes les réunions " + dateForFilter,
                            Toast.LENGTH_LONG).show();
                    mViewModel.setDateFilterType(dateForFilter);

                } else if (dateForFilter.length() !=10 && !dateForFilter.isEmpty() ) {
                    Toast.makeText(
                            MainActivity.this.getApplicationContext(),
                            "La date est invalide",
                            Toast.LENGTH_LONG).show();
                } else if (dateForFilter.length() == 10) {
                    Toast.makeText(
                            MainActivity.this.getApplicationContext(),
                            "Tu as choisi d'afficher les réunions de cette date : " + dateForFilter,
                            Toast.LENGTH_LONG).show();
                    mViewModel.setDateFilterType(dateForFilter);
                }
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