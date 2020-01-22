package com.example.mareu.view;

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
import com.example.mareu.view.helper.DateFilter;
import com.example.mareu.view.model.MeetingUiModel;
import com.example.mareu.view.helper.RoomFilterType;
import com.example.mareu.view.helper.SortingType;
import com.example.mareu.view.viewModel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.CallbackListener {

    private SortingType mSortingType;
    private RoomFilterType mRoomFilterType;
    private DateFilter mDateFilter;
    private MainViewModel mViewModel;
    private String mToastTextForChoiceDateFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);

        setupFloatingActionButton();
        setupToolbar();

        final MainAdapter adapter = new MainAdapter(this);

        configureRecyclerView(adapter);

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

        mViewModel.getMeetingUiModelsLiveData().observe(this, new Observer<List<MeetingUiModel>>() {
            @Override
            public void onChanged(List<MeetingUiModel> meetingListUiModel) {
                adapter.submitList(meetingListUiModel);
            }
        });

        mViewModel.getSortingTypeUiModelLiveData().observe(this, new Observer<SortingType>() {
            @Override
            public void onChanged(SortingType sortingType) {
                mSortingType = sortingType;
                alertDialogChoiceSort(sortingType);
            }
        });

        mViewModel.getRoomFilterTypeUiModelLiveData().observe(this, new Observer<RoomFilterType>() {
            @Override
            public void onChanged(RoomFilterType roomFilterType) {
                mRoomFilterType = roomFilterType;
                alertDialogChoiceRoomFilter(roomFilterType);
            }
        });

        mViewModel.getChoiceDateFilterUiModelLiveData().observe(this, new Observer<DateFilter>() {
            @Override
            public void onChanged(DateFilter dateFilter) {
                mDateFilter = dateFilter;
                alertDialogChoiceDateFilter(dateFilter);
            }
        });

        mViewModel.getToastTextForChoiceDateFilter().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String toastTextForChoiceDateFilter) {
                mToastTextForChoiceDateFilter = toastTextForChoiceDateFilter;
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_tb_toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCreateMeetingActivity();
            }
        });
    }

    private void configureRecyclerView(MainAdapter adapter) {
        RecyclerView recyclerView = findViewById(R.id.main_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void launchCreateMeetingActivity() {
        Intent intent = new Intent(this.getApplicationContext(), CreateMeetingActivity.class);
        startActivity(intent);
    }

    private void alertDialogChoiceSort(final SortingType sortingType) {

        // Setup Alert builder
        final AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle(sortingType.getTitle());

        myPopup.setSingleChoiceItems(sortingType.getNames(),
                sortingType.getSelectedIndex(), (new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }));

        // Setup "Valider" button
        myPopup.setPositiveButton(sortingType.getPositiveButtonText(), (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lw = ((AlertDialog) dialog).getListView();
                Object checkedItemObject = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                Toast.makeText(
                        MainActivity.this.getApplicationContext(),
                        sortingType.getToastChoiceSorting() + checkedItemObject,
                        Toast.LENGTH_LONG).show();

                mViewModel.setSortingType((String) checkedItemObject, mSortingType);
            }
        }));

        myPopup.setCancelable(false);
        myPopup.show();
    }

    private void alertDialogChoiceRoomFilter(final RoomFilterType roomFilterType) {

        // Setup Alert builder
        final AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle(roomFilterType.getTitle());

        myPopup.setSingleChoiceItems(roomFilterType.getListOFilterType(),
                roomFilterType.getSelectedIndex(), (new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }));

        // Setup validate button
        myPopup.setPositiveButton(roomFilterType.getPositiveButtonText(), (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lw = ((AlertDialog) dialog).getListView();
                Object checkedItemObject = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                Toast.makeText(
                        MainActivity.this.getApplicationContext(),
                        roomFilterType.getToastChoiceMeeting() + checkedItemObject,
                        Toast.LENGTH_LONG).show();
                mViewModel.setRoomFilterType((String) checkedItemObject, mRoomFilterType);
            }
        }));

        myPopup.setCancelable(false);
        myPopup.show();
    }

    private void alertDialogChoiceDateFilter(final DateFilter dateFilter) {

        // Setup Alert builder
        final AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle(dateFilter.getTitle());
        myPopup.setMessage(dateFilter.getMessage());

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        myPopup.setView(input);

        // Setup validate button
        myPopup.setPositiveButton(dateFilter.getPositiveButtonText(), (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SpannableString contentText = new SpannableString(input.getText());
                String dateToFilter = contentText.toString();

                mViewModel.compareDateToFilter(dateToFilter);

                Toast.makeText(
                        MainActivity.this.getApplicationContext(),
                        mToastTextForChoiceDateFilter + dateToFilter,
                        Toast.LENGTH_LONG).show();
            }
        }));

        myPopup.setCancelable(false);
        myPopup.show();
    }

    @Override
    public void onMeetingClicked(int meetingId) {
        mViewModel.deleteMeeting(meetingId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.toolbar_bt_sort_meeting) {
            mViewModel.displaySortingTypePopup();
        } else if (item.getItemId() == R.id.toolbar_bt_filter_room_meeting) {
            mViewModel.displayFilterRoomPopup();
        } else if (item.getItemId() == R.id.toolbar_bt_filter_date_meeting) {
            mViewModel.displayChoiceDateFilterPopup();
        }
        return super.onOptionsItemSelected(item);
    }
}