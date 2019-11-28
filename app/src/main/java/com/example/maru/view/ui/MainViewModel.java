package com.example.maru.view.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.model.FilterTypeUiModel;
import com.example.maru.view.ui.model.MeetingUiModel;
import com.example.maru.view.ui.model.SortingTypeUiModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.maru.view.ui.FilterType.ALL_ROOM;
import static com.example.maru.view.ui.FilterType.ROOM_1;
import static com.example.maru.view.ui.FilterType.ROOM_10;
import static com.example.maru.view.ui.FilterType.ROOM_2;
import static com.example.maru.view.ui.FilterType.ROOM_3;
import static com.example.maru.view.ui.FilterType.ROOM_4;
import static com.example.maru.view.ui.FilterType.ROOM_5;
import static com.example.maru.view.ui.FilterType.ROOM_6;
import static com.example.maru.view.ui.FilterType.ROOM_7;
import static com.example.maru.view.ui.FilterType.ROOM_8;
import static com.example.maru.view.ui.FilterType.ROOM_9;
import static com.example.maru.view.ui.SortingType.DATE_ASC;
import static com.example.maru.view.ui.SortingType.DATE_DSC;
import static com.example.maru.view.ui.SortingType.ROOM_ALPHABETICAL_ASC;
import static com.example.maru.view.ui.SortingType.ROOM_ALPHABETICAL_DSC;

public class MainViewModel extends ViewModel {

    private static final Comparator<MeetingJava> ROOM_COMPARATOR_MEETING_JAVA_ASC = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e1.getRoom() - e2.getRoom());
        }
    };
    private static final Comparator<MeetingJava> ROOM_COMPARATOR_MEETING_JAVA_DSC = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e2.getRoom() - e1.getRoom());
        }
    };
    private static final Comparator<MeetingJava> DATE_COMPARATOR_ASC = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e1.getDate().compareTo(e2.getDate()));
        }
    };
    private static final Comparator<MeetingJava> DATE_COMPARATOR_DSC = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e2.getDate().compareTo(e1.getDate()));
        }
    };

    private SortingTypeUiModel sortingTypeUiModel = new SortingTypeUiModel();
    private FilterTypeUiModel filterTypeUiModel = new FilterTypeUiModel();

    private int selectedSortingTypeIndex = 0;
    private int selectedFilterTypeIndex = 0;

    private final List<String> listOfItemSortMenu = new ArrayList<>();
    private final List<String> listOfItemFilterMenu = new ArrayList<>();

    private final LiveData<List<MeetingJava>> meetingLiveData = MeetingManager.getInstance().getMeetingLiveData();

    private MediatorLiveData<List<MeetingUiModel>> mUiModelsLiveData = new MediatorLiveData<>();
    private MutableLiveData<SortingType> mSortingTypeLiveData = new MutableLiveData<>();

    private MutableLiveData<FilterType> mFilterTypeLiveData = new MutableLiveData<>();

    private MutableLiveData<SortingTypeUiModel> mSortingTypeUiModelLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mSelectedSortingTypeIndexLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSelectedFilterTypeLiveData = new MutableLiveData<>();

    private MutableLiveData<FilterTypeUiModel> mFilterTypeUiModelLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mSelectedFilterTypeIndexLiveData = new MutableLiveData<>();

    public MainViewModel() {
        wireUpMediator();
    }

    private void wireUpMediator() {

        mUiModelsLiveData.addSource(meetingLiveData, new Observer<List<MeetingJava>>() {
            @Override
            public void onChanged(List<MeetingJava> meetingJavas) {
                mUiModelsLiveData.setValue(combineMeeting(
                        meetingJavas,
                        mSortingTypeLiveData.getValue(),
                        mSelectedFilterTypeLiveData.getValue()));
            }
        });

        mUiModelsLiveData.addSource(mSortingTypeLiveData, new Observer<SortingType>() {
            @Override
            public void onChanged(SortingType sortingType) {
                mUiModelsLiveData.setValue(combineMeeting(meetingLiveData.getValue(), sortingType));
            }
        });

        mUiModelsLiveData.addSource(mSelectedSortingTypeIndexLiveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                selectedSortingTypeIndex = integer;
            }
        });

        mUiModelsLiveData.addSource(mSelectedFilterTypeLiveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer selectedMeetingRoomNumber) {
                mUiModelsLiveData.setValue(combineMeeting(
                        meetingLiveData.getValue(),
                        mSortingTypeLiveData.getValue(),
                        selectedMeetingRoomNumber));
            }
        });

        mUiModelsLiveData.addSource(mFilterTypeLiveData, new Observer<FilterType>() {
            @Override
            public void onChanged(FilterType filterType) {
                mUiModelsLiveData.setValue(combineMeeting(
                        meetingLiveData.getValue(),
                        mSortingTypeLiveData.getValue(),
                        mSelectedFilterTypeLiveData.getValue()));
            }
        });
    }

    @Nullable
    private List<MeetingUiModel> combineMeeting(
            @Nullable List<MeetingJava> meetings,
            @Nullable SortingType sortingType,
            @Nullable Integer selectedMeetingRoomNumber) {

        if (meetings == null) {
            return null;
        }

        if (sortingType == null || sortingType == ROOM_ALPHABETICAL_ASC) {

            Collections.sort(meetings, ROOM_COMPARATOR_MEETING_JAVA_ASC);

        } else if (sortingType == ROOM_ALPHABETICAL_DSC) {

            Collections.sort(meetings, ROOM_COMPARATOR_MEETING_JAVA_DSC);

        } else if (sortingType == DATE_ASC) {

            Collections.sort(meetings, DATE_COMPARATOR_ASC);

        } else if (sortingType == DATE_DSC) {

            Collections.sort(meetings, DATE_COMPARATOR_DSC);
        }

        List<MeetingUiModel> result = new ArrayList<>();

        for (MeetingJava meetingJava : meetings) {

            if (selectedMeetingRoomNumber == null || selectedMeetingRoomNumber == meetingJava.getRoom()) {
                MeetingUiModel meetingUiModel = new MeetingUiModel(
                        meetingJava.getId(),
                        meetingJava.getDate().toString(),
                        meetingJava.getHour(),
                        Integer.toString(meetingJava.getRoom()),
                        meetingJava.getSubject(),
                        meetingJava.getListOfEmailOfParticipant().toString());

                result.add(meetingUiModel);
            }
        }

        return result;
    }

    void setSortingType(String sortChoice, SortingTypeUiModel sortingTypeUiModel) {
        switch (sortChoice) {
            case "Croissant salle":
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_ASC);
                selectedSortingTypeIndex = 0;
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                mSelectedSortingTypeIndexLiveData.setValue(selectedSortingTypeIndex);
                break;
            case "Decroissant salle":
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_DSC);
                selectedSortingTypeIndex = 1;
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                mSelectedSortingTypeIndexLiveData.setValue(selectedSortingTypeIndex);
                break;
            case "Croissant date":
                mSortingTypeLiveData.setValue(DATE_ASC);
                selectedSortingTypeIndex = 2;
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                mSelectedSortingTypeIndexLiveData.setValue(selectedSortingTypeIndex);
                break;
            case "Decroissant date":
                mSortingTypeLiveData.setValue(DATE_DSC);
                selectedSortingTypeIndex = 3;
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                mSelectedSortingTypeIndexLiveData.setValue(selectedSortingTypeIndex);
                break;
        }
    }

    void setFilterType(String filterChoice, FilterTypeUiModel filterTypeUiModel) {
        switch (filterChoice) {
            case "toutes les salles":
                mFilterTypeLiveData.setValue(ALL_ROOM);
                selectedFilterTypeIndex = 0;
                mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
                filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
                mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
                break;
            case "salle 1":
                mFilterTypeLiveData.setValue(ROOM_1);
                selectedFilterTypeIndex = 1;
                mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
                filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
                mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
                break;
            case "salle 2":
                mFilterTypeLiveData.setValue(ROOM_2);
                selectedFilterTypeIndex = 2;
                mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
                filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
                mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
                break;
            case "salle 3":
                mFilterTypeLiveData.setValue(ROOM_3);
                selectedFilterTypeIndex = 3;
                mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
                filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
                mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
                break;
            case "salle 4":
                mFilterTypeLiveData.setValue(ROOM_4);
                selectedFilterTypeIndex = 4;
                mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
                filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
                mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
                break;
            case "salle 5":
                mFilterTypeLiveData.setValue(ROOM_5);
                selectedFilterTypeIndex = 5;
                mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
                filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
                mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
                break;
            case "salle 6":
                mFilterTypeLiveData.setValue(ROOM_6);
                selectedFilterTypeIndex = 6;
                mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
                filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
                mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
                break;
            case "salle 7":
                mFilterTypeLiveData.setValue(ROOM_7);
                selectedFilterTypeIndex = 7;
                mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
                filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
                mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
                break;
            case "salle 8":
                mFilterTypeLiveData.setValue(ROOM_8);
                selectedFilterTypeIndex = 8;
                mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
                filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
                mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
                break;
            case "salle 9":
                mFilterTypeLiveData.setValue(ROOM_9);
                selectedFilterTypeIndex = 9;
                mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
                filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
                mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
                break;
            case "salle 10":
                mFilterTypeLiveData.setValue(ROOM_10);
                selectedFilterTypeIndex = 10;
                mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
                filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
                mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
                break;
        }
    }

    LiveData<List<MeetingUiModel>> getUiModelsLiveData() { return mUiModelsLiveData; }

    LiveData<SortingTypeUiModel> getmSortingTypeUiModelLiveData() { return mSortingTypeUiModelLiveData; }

    LiveData<FilterTypeUiModel> getmFilterTypeUiModelLiveData() { return mFilterTypeUiModelLiveData; }

    LiveData<Integer> getSelectedSortingTypeIndexLiveDate() { return mSelectedSortingTypeIndexLiveData; }

    LiveData<Integer> getSelectedFilterTypeIndexLiveDate() {return mSelectedFilterTypeIndexLiveData; }

    void displaySortingTypePopup() {

        if (listOfItemSortMenu.size() == 4) {
            mSortingTypeUiModelLiveData.setValue(getSortingTypeUiModel());
        } else {
            listOfItemSortMenu.add("Croissant salle");
            listOfItemSortMenu.add("Decroissant salle");
            listOfItemSortMenu.add("Croissant date");
            listOfItemSortMenu.add("Decroissant date");
            setSortingTypeUiModel();
        }
    }

    private SortingTypeUiModel getSortingTypeUiModel() {
        return sortingTypeUiModel;
    }

    private SortingTypeUiModel setSortingTypeUiModel() {
        sortingTypeUiModel.setNames(listOfItemSortMenu);
        sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
        mSortingTypeUiModelLiveData.setValue(sortingTypeUiModel);
        return sortingTypeUiModel;
    }

    // TODO : Filtre des réunions par date ou par lieu
    // TODO : soi deux bouttons dans le menu, un pour afficher un popup avec un choix de la salle en liste
    // TODO : et un autre boutton dans le menu (total de 3 buttons) qui ouvre un popup avec un edit text ou on tape la date choisi

    void displayFilterTypePopup() {

        if (listOfItemFilterMenu.size() == 11) {
            mFilterTypeUiModelLiveData.setValue(getFilterTypeUiModel());
        } else {
            listOfItemFilterMenu.add("toutes les salles");
            listOfItemFilterMenu.add("salle 1");
            listOfItemFilterMenu.add("salle 2");
            listOfItemFilterMenu.add("salle 3");
            listOfItemFilterMenu.add("salle 4");
            listOfItemFilterMenu.add("salle 5");
            listOfItemFilterMenu.add("salle 6");
            listOfItemFilterMenu.add("salle 7");
            listOfItemFilterMenu.add("salle 8");
            listOfItemFilterMenu.add("salle 9");
            listOfItemFilterMenu.add("salle 10");
            setFilterTypeUiModel();
        }
    }

    private FilterTypeUiModel getFilterTypeUiModel() {
        return filterTypeUiModel;
    }

    private FilterTypeUiModel setFilterTypeUiModel() {
        filterTypeUiModel.setNames(listOfItemFilterMenu);
        filterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
        mFilterTypeUiModelLiveData.setValue(filterTypeUiModel);
        return filterTypeUiModel;
    }

    void deleteMeeting(int meetingId) {
        MeetingManager.getInstance().deleteMeeting(meetingId);
    }
}