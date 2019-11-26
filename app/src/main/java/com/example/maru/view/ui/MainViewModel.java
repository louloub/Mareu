package com.example.maru.view.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.model.MeetingUiModel;
import com.example.maru.view.ui.model.SortingTypeUiModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    private final LiveData<List<MeetingJava>> meetingLiveData = MeetingManager.getInstance().getMeetingLiveData(); // TODO INJECT THIS INSTEAD
    private final List<String> list = new ArrayList<>();
    private MediatorLiveData<List<MeetingUiModel>> mUiModelsLiveData = new MediatorLiveData<>();
    private MutableLiveData<SortingType> mSortingTypeLiveData = new MutableLiveData<>();
    private MutableLiveData<SortingTypeUiModel> mSortingTypeUiModelLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mSelectedSortingTypeIndexLiveData = new MutableLiveData<>();
    private int selectedSortingTypeIndex = 0;
    private SortingTypeUiModel sortingTypeUiModel = new SortingTypeUiModel();
    private final MutableLiveData<Integer> mSelectedFilterTypeLiveData = new MutableLiveData<>();

    public MainViewModel() {
        wireUpMediator();
    }

    private void wireUpMediator() {

        mUiModelsLiveData.addSource(meetingLiveData, new Observer<List<MeetingJava>>() {
            @Override
            public void onChanged(List<MeetingJava> meetingJavas) {
                mUiModelsLiveData.setValue(combineMeeting(meetingJavas, mSortingTypeLiveData.getValue(), mSelectedFilterTypeLiveData.getValue()));
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
                mUiModelsLiveData.setValue(combineMeeting(meetingLiveData.getValue(), mSortingTypeLiveData.getValue(),selectedMeetingRoomNumber));
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

            if (selectedMeetingRoomNumber == null || selectedMeetingRoomNumber == meetingJava.getRoom()){

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

    LiveData<List<MeetingUiModel>> getUiModelsLiveData() {
        return mUiModelsLiveData;
    }

    LiveData<SortingTypeUiModel> getmSortingTypeUiModelLiveData() {
        return mSortingTypeUiModelLiveData;
    }

    LiveData<Integer> getSelectedSortingTypeIndexLiveDate() {
        return mSelectedSortingTypeIndexLiveData;
    }

    void displaySortingTypePopup() {

        if (list.size() == 4) {
            mSortingTypeUiModelLiveData.setValue(getSortingTypeUiModel());
        } else {
            list.add("Croissant salle");
            list.add("Decroissant salle");
            list.add("Croissant date");
            list.add("Decroissant date");
            setSortingTypeUiModel();
        }
    }

    private SortingTypeUiModel getSortingTypeUiModel() {
        return sortingTypeUiModel;
    }

    private SortingTypeUiModel setSortingTypeUiModel() {
        sortingTypeUiModel.setNames(list);
        sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
        mSortingTypeUiModelLiveData.setValue(sortingTypeUiModel);
        return sortingTypeUiModel;
    }

    void deleteMeeting(int meetingId) {
        MeetingManager.getInstance().deleteMeeting(meetingId);
    }

    public void setFilterType() {
        // TODO : rempalcer 0 par du dynamique
        mSelectedFilterTypeLiveData.setValue(1);
    }
}