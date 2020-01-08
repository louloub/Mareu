package com.example.mareu.view.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.mareu.service.model.Meeting;
import com.example.mareu.utility.MeetingManager;
import com.example.mareu.utility.SingleLiveEvent;
import com.example.mareu.view.ui.model.DateFilterUiModel;
import com.example.mareu.view.ui.model.RoomFilterTypeUiModel;
import com.example.mareu.view.ui.model.MeetingUiModel;
import com.example.mareu.view.ui.model.SortingTypeUiModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.example.mareu.view.ui.RoomFilterType.ALL_ROOM;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_1;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_10;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_2;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_3;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_4;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_5;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_6;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_7;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_8;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_9;
import static com.example.mareu.view.ui.SortingType.DATE_ASC;
import static com.example.mareu.view.ui.SortingType.DATE_DSC;
import static com.example.mareu.view.ui.SortingType.ROOM_ALPHABETICAL_ASC;
import static com.example.mareu.view.ui.SortingType.ROOM_ALPHABETICAL_DSC;

public class MainViewModel extends ViewModel {

    private static final Comparator<Meeting> ROOM_COMPARATOR_MEETING_ASC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e1.getRoom() - e2.getRoom());
        }
    };
    private static final Comparator<Meeting> ROOM_COMPARATOR_MEETING_DSC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e2.getRoom() - e1.getRoom());
        }
    };
    private static final Comparator<Meeting> DATE_COMPARATOR_ASC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e1.getDate().compareTo(e2.getDate()));
        }
    };
    private static final Comparator<Meeting> DATE_COMPARATOR_DSC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e2.getDate().compareTo(e1.getDate()));
        }
    };

    private final SortingTypeUiModel sortingTypeUiModel = new SortingTypeUiModel();
    private final RoomFilterTypeUiModel mRoomFilterTypeUiModel = new RoomFilterTypeUiModel();
    private final DateFilterUiModel mChoiceDateFilterUiModel = new DateFilterUiModel();

    private int selectedSortingTypeIndex = 0;
    private int selectedFilterTypeIndex = 0;

    private final List<String> listOfItemSortMenu = new ArrayList<>();
    private final List<String> listOfItemFilterRoomMenu = new ArrayList<>();

    private final LiveData<List<Meeting>> mMeetingListLiveData;
    private final MediatorLiveData<List<MeetingUiModel>> mMeetingUiModelsLiveData = new MediatorLiveData<>();

    private final SingleLiveEvent<SortingType> mSortingTypeLiveData = new SingleLiveEvent<>();
    private final SingleLiveEvent<SortingTypeUiModel> mSortingTypeUiModelLiveData = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> mSelectedSortingTypeIndexLiveData = new SingleLiveEvent<>();

    private final MutableLiveData<RoomFilterType> mRoomFilterTypeLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<RoomFilterTypeUiModel> mRoomFilterTypeUiModelLiveData = new SingleLiveEvent<>();

    private final MutableLiveData<Integer> mSelectedFilterTypeLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<Integer> mSelectedFilterTypeIndexLiveData = new SingleLiveEvent<>();

    private final SingleLiveEvent<DateFilterUiModel> mChoiceDateFilterUiModelData = new SingleLiveEvent<>();

    LiveData<List<MeetingUiModel>> getMeetingUiModelsLiveData() { return mMeetingUiModelsLiveData; }
    LiveData<SortingTypeUiModel> getSortingTypeUiModelLiveData() { return mSortingTypeUiModelLiveData; }

    LiveData<RoomFilterTypeUiModel> getRoomFilterTypeUiModelLiveData() { return mRoomFilterTypeUiModelLiveData; }
    LiveData<DateFilterUiModel> getChoiceDateFilterUiModelLiveData() {return mChoiceDateFilterUiModelData;}

    private RoomFilterTypeUiModel getRoomFilterTypeUiModel() { return mRoomFilterTypeUiModel;}

    public MainViewModel(@NonNull MeetingManager meetingManager) {
        mMeetingListLiveData = meetingManager.getMeetingListLiveData();
        wireUpMediator();
    }

    private void wireUpMediator() {

        mMeetingUiModelsLiveData.addSource(mMeetingListLiveData, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                meetings,
                                mSortingTypeLiveData.getValue(),
                                mSelectedFilterTypeLiveData.getValue()
                        )
                );
            }
        });

        mMeetingUiModelsLiveData.addSource(mSortingTypeLiveData, new Observer<SortingType>() {
            @Override
            public void onChanged(SortingType sortingType) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                mMeetingListLiveData.getValue(),
                                mSortingTypeLiveData.getValue(),
                                mSelectedFilterTypeLiveData.getValue()
                        )
                );
            }
        });

        mMeetingUiModelsLiveData.addSource(mSelectedSortingTypeIndexLiveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                selectedSortingTypeIndex = integer;
            }
        });

        mMeetingUiModelsLiveData.addSource(mSelectedFilterTypeLiveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer selectedMeetingRoomNumber) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                mMeetingListLiveData.getValue(),
                                mSortingTypeLiveData.getValue(),
                                selectedMeetingRoomNumber)
                );
            }
        });

        mMeetingUiModelsLiveData.addSource(mRoomFilterTypeLiveData, new Observer<RoomFilterType>() {
            @Override
            public void onChanged(RoomFilterType roomFilterType) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                mMeetingListLiveData.getValue(),
                                mSortingTypeLiveData.getValue(),
                                mSelectedFilterTypeLiveData.getValue()
                        )
                );
            }
        });
    }

    @Nullable
    private List<MeetingUiModel> combineMeeting(
            @Nullable List<Meeting> meetings,
            @Nullable SortingType sortingType,
            @Nullable Integer selectedMeetingRoomNumber) {

        if (meetings == null) {
            return null;
        }

        if (sortingType == null || sortingType == ROOM_ALPHABETICAL_ASC) {

            Collections.sort(meetings, ROOM_COMPARATOR_MEETING_ASC);

        } else if (sortingType == ROOM_ALPHABETICAL_DSC) {

            Collections.sort(meetings, ROOM_COMPARATOR_MEETING_DSC);

        } else if (sortingType == DATE_ASC) {

            Collections.sort(meetings, DATE_COMPARATOR_ASC);

        } else if (sortingType == DATE_DSC) {

            Collections.sort(meetings, DATE_COMPARATOR_DSC);
        }

        List<MeetingUiModel> result = new ArrayList<>();

        for (Meeting meeting : meetings) {

            if (selectedMeetingRoomNumber == null || selectedMeetingRoomNumber == meeting.getRoom()) {
                createMeetingUiModelInCombineMeeting(result, meeting);
            } else if (selectedMeetingRoomNumber == 0) {
                createMeetingUiModelInCombineMeeting(result, meeting);
            }
        }

        return result;
    }

    private void createMeetingUiModelInCombineMeeting(List<MeetingUiModel> result, Meeting meeting) {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> listOfEmailOfParticipant = meeting.getListOfEmailOfParticipant();

        for (int i = 0; i < listOfEmailOfParticipant.size(); i++) {
            String participant = listOfEmailOfParticipant.get(i);
            stringBuilder.append(participant);
            if (i+1<listOfEmailOfParticipant.size()) {
                stringBuilder.append(", ");
            }
        }

        MeetingUiModel meetingUiModel = new MeetingUiModel(
                meeting.getId(),
                meeting.getDate().toString(),
                meeting.getHour(),
                Integer.toString(meeting.getRoom()),
                meeting.getSubject(),
                stringBuilder.toString());

        result.add(meetingUiModel);
    }

    void setSortingType(String sortChoice, SortingTypeUiModel sortingTypeUiModel) {
        switch (sortChoice) {
            case "Croissant salle":
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_ASC);
                selectedSortingTypeIndex = 0;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
            case "Decroissant salle":
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_DSC);
                selectedSortingTypeIndex = 1;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
            case "Croissant date":
                mSortingTypeLiveData.setValue(DATE_ASC);
                selectedSortingTypeIndex = 2;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
            case "Decroissant date":
                mSortingTypeLiveData.setValue(DATE_DSC);
                selectedSortingTypeIndex = 3;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
        }
    }

    private void setValueSortingTypeUiModel(SortingTypeUiModel sortingTypeUiModel) {
        sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
        mSelectedSortingTypeIndexLiveData.setValue(selectedSortingTypeIndex);
    }

    void setRoomFilterType(String filterChoice, RoomFilterTypeUiModel roomFilterTypeUiModel) {
        switch (filterChoice) {
            // TODO : changer "toutes les salles" en un enum
            case "toutes les salles":
                mRoomFilterTypeLiveData.setValue(ALL_ROOM);
                selectedFilterTypeIndex = 0;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case "salle 1":
                mRoomFilterTypeLiveData.setValue(ROOM_1);
                selectedFilterTypeIndex = 1;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case "salle 2":
                mRoomFilterTypeLiveData.setValue(ROOM_2);
                selectedFilterTypeIndex = 2;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case "salle 3":
                mRoomFilterTypeLiveData.setValue(ROOM_3);
                selectedFilterTypeIndex = 3;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case "salle 4":
                mRoomFilterTypeLiveData.setValue(ROOM_4);
                selectedFilterTypeIndex = 4;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case "salle 5":
                mRoomFilterTypeLiveData.setValue(ROOM_5);
                selectedFilterTypeIndex = 5;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case "salle 6":
                mRoomFilterTypeLiveData.setValue(ROOM_6);
                selectedFilterTypeIndex = 6;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case "salle 7":
                mRoomFilterTypeLiveData.setValue(ROOM_7);
                selectedFilterTypeIndex = 7;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case "salle 8":
                mRoomFilterTypeLiveData.setValue(ROOM_8);
                selectedFilterTypeIndex = 8;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case "salle 9":
                mRoomFilterTypeLiveData.setValue(ROOM_9);
                selectedFilterTypeIndex = 9;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case "salle 10":
                mRoomFilterTypeLiveData.setValue(ROOM_10);
                selectedFilterTypeIndex = 10;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
        }
    }

    void setDateFilterType(String dateForFilter) {

        int size = 0;
        int index = 0;

        if (mMeetingListLiveData.getValue() != null) {
            size = Objects.requireNonNull(mMeetingListLiveData.getValue()).size();
        }

        List<MeetingUiModel> meetingUiModelListWithDateFilter = new ArrayList<>();
        List<MeetingUiModel> meetingUiModelListWithoutDateFilter = new ArrayList<>();

        while (mMeetingListLiveData.getValue() != null && size > index) {

            MeetingUiModel meetingUiModelWithoutValidDateFilter = createMeetingUiModel(index);

            meetingUiModelListWithoutDateFilter.add(meetingUiModelWithoutValidDateFilter);

            if (mMeetingListLiveData.getValue().get(index).getDate().toString().equals(dateForFilter)) {

                MeetingUiModel meetingUiModelWithValidDateFilter = createMeetingUiModel(index);

                meetingUiModelListWithDateFilter.add(meetingUiModelWithValidDateFilter);
                mMeetingUiModelsLiveData.setValue(meetingUiModelListWithDateFilter);

            } else if (dateForFilter.isEmpty()) {
                mMeetingUiModelsLiveData.setValue(meetingUiModelListWithoutDateFilter);
            } else if (dateForFilter.length() != 10) {
                mMeetingUiModelsLiveData.setValue(meetingUiModelListWithDateFilter);
            } else {
                mMeetingUiModelsLiveData.setValue(meetingUiModelListWithDateFilter);
            }
            index++;
        } // END WHILE
    }

    // TODO : virer cette methode
    @NotNull
    private MeetingUiModel createMeetingUiModel(int index) {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> listOfEmailOfParticipant = mMeetingListLiveData.getValue().get(index).getListOfEmailOfParticipant();

        for (int i = 0; i < listOfEmailOfParticipant.size(); i++) {
            String participant = listOfEmailOfParticipant.get(i);
            stringBuilder.append(participant);
            if (i+1<listOfEmailOfParticipant.size()) {
                stringBuilder.append(", ");
            }
        }

        return new MeetingUiModel(
                mMeetingListLiveData.getValue().get(index).getId(),
                mMeetingListLiveData.getValue().get(index).getDate().toString(),
                mMeetingListLiveData.getValue().get(index).getHour(),
                Integer.toString(mMeetingListLiveData.getValue().get(index).getRoom()),
                mMeetingListLiveData.getValue().get(index).getSubject(),
                stringBuilder.toString());
    }

    private void setValueFilterUiModel(RoomFilterTypeUiModel roomFilterTypeUiModel) {
        mSelectedFilterTypeLiveData.setValue(selectedFilterTypeIndex);
        roomFilterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
        mSelectedFilterTypeIndexLiveData.setValue(selectedFilterTypeIndex);
    }

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

    private void setSortingTypeUiModel() {
        sortingTypeUiModel.setTitle("Choisis le trie que tu souhaites");
        sortingTypeUiModel.setPositiveButtonText("Valider");
        sortingTypeUiModel.setToastChoiceSorting("Tu as choisi ");
        sortingTypeUiModel.setNames(listOfItemSortMenu);
        sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
        mSortingTypeUiModelLiveData.setValue(sortingTypeUiModel);
    }

    void displayFilterRoomPopup() {

        if (listOfItemFilterRoomMenu.size() == 11) {
            mRoomFilterTypeUiModelLiveData.setValue(getRoomFilterTypeUiModel());
        } else {
            listOfItemFilterRoomMenu.add("toutes les salles");
            listOfItemFilterRoomMenu.add("salle 1");
            listOfItemFilterRoomMenu.add("salle 2");
            listOfItemFilterRoomMenu.add("salle 3");
            listOfItemFilterRoomMenu.add("salle 4");
            listOfItemFilterRoomMenu.add("salle 5");
            listOfItemFilterRoomMenu.add("salle 6");
            listOfItemFilterRoomMenu.add("salle 7");
            listOfItemFilterRoomMenu.add("salle 8");
            listOfItemFilterRoomMenu.add("salle 9");
            listOfItemFilterRoomMenu.add("salle 10");
            setRoomFilterTypeUiModel();
        }
    }

    void displayChoiceDateFilterPopup(){
        mChoiceDateFilterUiModel.setTitle("Choisis la date à filtrer");
        mChoiceDateFilterUiModel.setMessage("Exemple : 2019-12-01");
        mChoiceDateFilterUiModel.setPositiveButtonText("Valider");
        mChoiceDateFilterUiModel.setToastForDisplayAllMeeting("Tu as choisi d'afficher toutes les réunions ");
        mChoiceDateFilterUiModel.setToastForInvalideDate("La date est invalide");
        mChoiceDateFilterUiModel.setToastForValideDate("Tu as choisi d'afficher les réunions de cette date : ");
        mChoiceDateFilterUiModelData.setValue(mChoiceDateFilterUiModel);
    }

    private void setRoomFilterTypeUiModel() {
        mRoomFilterTypeUiModel.setTitle("Choisis la salle à filtrer");
        mRoomFilterTypeUiModel.setPositiveButtonText("Valider");
        mRoomFilterTypeUiModel.setToastChoiceMeeting("Tu as choisi d'afficher les réunions : ");
        mRoomFilterTypeUiModel.setNames(listOfItemFilterRoomMenu);
        mRoomFilterTypeUiModel.setSelectedIndex(selectedFilterTypeIndex);
        mRoomFilterTypeUiModelLiveData.setValue(mRoomFilterTypeUiModel);
    }

    void deleteMeeting(int meetingId) {
        MeetingManager.getInstance().deleteMeeting(meetingId);
    }
}