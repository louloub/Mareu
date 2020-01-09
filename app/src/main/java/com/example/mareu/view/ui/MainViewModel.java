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
import com.example.mareu.view.ui.model.MeetingUiModel;
import com.example.mareu.view.ui.model.RoomFilterTypeUiModel;
import com.example.mareu.view.ui.model.SortingTypeUiModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    private static final String ROOM_ALPHABETICAL_ASC_STRING = "Croissant salle";
    private static final String ROOM_ALPHABETICAL_DSC_STRING = "Decroissant salle";
    private static final String DATE_ASC_STRING = "Croissant date";
    private static final String DATE_DSC_STRING = "Decroissant date";

    private static final String ALL_ROOM_STRING = "toutes les salles";
    private static final String ROOM_1_STRING = "salle 1";
    private static final String ROOM_2_STRING = "salle 2";
    private static final String ROOM_3_STRING = "salle 3";
    private static final String ROOM_4_STRING = "salle 4";
    private static final String ROOM_5_STRING = "salle 5";
    private static final String ROOM_6_STRING = "salle 6";
    private static final String ROOM_7_STRING = "salle 7";
    private static final String ROOM_8_STRING = "salle 8";
    private static final String ROOM_9_STRING = "salle 9";
    private static final String ROOM_10_STRING = "salle 10";

    private final RoomFilterTypeUiModel mRoomFilterTypeUiModel = new RoomFilterTypeUiModel();
    private final DateFilterUiModel mChoiceDateFilterUiModel = new DateFilterUiModel();
    // private final String[] mListOfItemSortMenu;
    private final List<String> mListOfItemFilterRoomMenu = new ArrayList<>();
    private final LiveData<List<Meeting>> mMeetingListLiveData;
    private final MediatorLiveData<List<MeetingUiModel>> mMeetingUiModelsLiveData = new MediatorLiveData<>();
    private final SingleLiveEvent<SortingType> mSortingTypeLiveData = new SingleLiveEvent<>();
    private final SingleLiveEvent<SortingTypeUiModel> mSortingTypeUiModelLiveData = new SingleLiveEvent<>();
    private final MutableLiveData<RoomFilterType> mRoomFilterTypeLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<RoomFilterTypeUiModel> mRoomFilterTypeUiModelLiveData = new SingleLiveEvent<>();
    private final MutableLiveData<Integer> mSelectedFilterTypeLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<DateFilterUiModel> mChoiceDateFilterUiModelData = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mToastTextForChoiceDateFilter = new SingleLiveEvent<>();
    private int mSelectedSortingTypeIndex = 0;
    private int mSelectedFilterTypeIndex = 0;

    public MainViewModel(@NonNull MeetingManager meetingManager) {
        mMeetingListLiveData = meetingManager.getMeetingListLiveData();
        wireUpMediator();
        // mListOfItemSortMenu = new String[4];
    }

    LiveData<List<MeetingUiModel>> getMeetingUiModelsLiveData() {
        return mMeetingUiModelsLiveData;
    }

    LiveData<SortingTypeUiModel> getSortingTypeUiModelLiveData() {
        return mSortingTypeUiModelLiveData;
    }

    LiveData<RoomFilterTypeUiModel> getRoomFilterTypeUiModelLiveData() {
        return mRoomFilterTypeUiModelLiveData;
    }

    LiveData<DateFilterUiModel> getChoiceDateFilterUiModelLiveData() {
        return mChoiceDateFilterUiModelData;
    }

    LiveData<String> getToastTextForChoiceDateFilter(){
        return mToastTextForChoiceDateFilter;
    }

    private RoomFilterTypeUiModel getRoomFilterTypeUiModel() {
        return mRoomFilterTypeUiModel;
    }

    /*private SortingTypeUiModel getSortingTypeUiModel() {
        return mSortingTypeUiModel;
    }*/

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

    // TODO : virer cette methode ou l'autre
    private void createMeetingUiModelInCombineMeeting(List<MeetingUiModel> result, Meeting meeting) {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> listOfEmailOfParticipant = meeting.getListOfEmailOfParticipant();

        for (int i = 0; i < listOfEmailOfParticipant.size(); i++) {
            String participant = listOfEmailOfParticipant.get(i);
            stringBuilder.append(participant);
            if (i + 1 < listOfEmailOfParticipant.size()) {
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

    // TODO : virer cette methode ou l'autre
    @NotNull
    private MeetingUiModel createMeetingUiModel(int index) {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> listOfEmailOfParticipant = mMeetingListLiveData.getValue().get(index).getListOfEmailOfParticipant();

        for (int i = 0; i < listOfEmailOfParticipant.size(); i++) {
            String participant = listOfEmailOfParticipant.get(i);
            stringBuilder.append(participant);
            if (i + 1 < listOfEmailOfParticipant.size()) {
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

    // SORTING TYPE
    void setSortingType(String sortChoice, SortingTypeUiModel sortingTypeUiModel) {
        switch (sortChoice) {
            case ROOM_ALPHABETICAL_ASC_STRING:
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_ASC);
                mSelectedSortingTypeIndex = 0;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
            case ROOM_ALPHABETICAL_DSC_STRING:
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_DSC);
                mSelectedSortingTypeIndex = 1;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
            case DATE_ASC_STRING:
                mSortingTypeLiveData.setValue(DATE_ASC);
                mSelectedSortingTypeIndex = 2;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
            case DATE_DSC_STRING:
                mSortingTypeLiveData.setValue(DATE_DSC);
                mSelectedSortingTypeIndex = 3;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
        }
    }

    private void setValueSortingTypeUiModel(SortingTypeUiModel sortingTypeUiModel) {
        sortingTypeUiModel.setSelectedIndex(mSelectedSortingTypeIndex);
    }

    private void setSortingTypeUiModel() {
        SortingTypeUiModel mSortingTypeUiModel = new SortingTypeUiModel();
        String[] mListOfItemSortMenu = new String[4];

        mSortingTypeUiModel.setTitle("Choisis le trie que tu souhaites");
        mSortingTypeUiModel.setPositiveButtonText("Valider");
        mSortingTypeUiModel.setToastChoiceSorting("Tu as choisi ");

        mListOfItemSortMenu[0] = ROOM_ALPHABETICAL_ASC_STRING;
        mListOfItemSortMenu[1] = ROOM_ALPHABETICAL_DSC_STRING;
        mListOfItemSortMenu[2] = DATE_ASC_STRING;
        mListOfItemSortMenu[3] = DATE_DSC_STRING;

        mSortingTypeUiModel.setNames(mListOfItemSortMenu);
        mSortingTypeUiModel.setSelectedIndex(mSelectedSortingTypeIndex);

        mSortingTypeUiModelLiveData.setValue(mSortingTypeUiModel);
    }

    void displaySortingTypePopup() {

        if (mSortingTypeUiModelLiveData.getValue()==null) {
            setSortingTypeUiModel();
        } else {
            mSortingTypeUiModelLiveData.setValue(getSortingTypeUiModelLiveData().getValue());
        }
    }

    // ROOM FILTER
    private void setRoomFilterTypeUiModel() {
        mRoomFilterTypeUiModel.setTitle("Choisis la salle à filtrer");
        mRoomFilterTypeUiModel.setPositiveButtonText("Valider");
        mRoomFilterTypeUiModel.setToastChoiceMeeting("Tu as choisi d'afficher les réunions : ");
        mRoomFilterTypeUiModel.setNames(mListOfItemFilterRoomMenu);
        mRoomFilterTypeUiModel.setSelectedIndex(mSelectedFilterTypeIndex);
        mRoomFilterTypeUiModelLiveData.setValue(mRoomFilterTypeUiModel);
    }

    private void setValueFilterUiModel(RoomFilterTypeUiModel roomFilterTypeUiModel) {
        mSelectedFilterTypeLiveData.setValue(mSelectedFilterTypeIndex);
        roomFilterTypeUiModel.setSelectedIndex(mSelectedFilterTypeIndex);
    }

    void setRoomFilterType(String filterChoice, RoomFilterTypeUiModel roomFilterTypeUiModel) {
        switch (filterChoice) {
            case ALL_ROOM_STRING:
                mRoomFilterTypeLiveData.setValue(ALL_ROOM);
                mSelectedFilterTypeIndex = 0;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_1_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_1);
                mSelectedFilterTypeIndex = 1;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_2_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_2);
                mSelectedFilterTypeIndex = 2;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_3_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_3);
                mSelectedFilterTypeIndex = 3;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_4_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_4);
                mSelectedFilterTypeIndex = 4;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_5_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_5);
                mSelectedFilterTypeIndex = 5;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_6_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_6);
                mSelectedFilterTypeIndex = 6;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_7_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_7);
                mSelectedFilterTypeIndex = 7;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_8_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_8);
                mSelectedFilterTypeIndex = 8;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_9_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_9);
                mSelectedFilterTypeIndex = 9;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_10_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_10);
                mSelectedFilterTypeIndex = 10;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
        }
    }

    void displayFilterRoomPopup() {

        if (mListOfItemFilterRoomMenu.size() == 11) {
            mRoomFilterTypeUiModelLiveData.setValue(getRoomFilterTypeUiModel());
        } else {
            mListOfItemFilterRoomMenu.add(ALL_ROOM_STRING);
            mListOfItemFilterRoomMenu.add(ROOM_1_STRING);
            mListOfItemFilterRoomMenu.add(ROOM_2_STRING);
            mListOfItemFilterRoomMenu.add(ROOM_3_STRING);
            mListOfItemFilterRoomMenu.add(ROOM_4_STRING);
            mListOfItemFilterRoomMenu.add(ROOM_5_STRING);
            mListOfItemFilterRoomMenu.add(ROOM_6_STRING);
            mListOfItemFilterRoomMenu.add(ROOM_7_STRING);
            mListOfItemFilterRoomMenu.add(ROOM_8_STRING);
            mListOfItemFilterRoomMenu.add(ROOM_9_STRING);
            mListOfItemFilterRoomMenu.add(ROOM_10_STRING);
            setRoomFilterTypeUiModel();
        }
    }

    // DATE FILTER
    void setDateFilterType(String dateForFilter) {

        int size = 0;
        int index = 0;

        if (mMeetingListLiveData.getValue() != null) {
            size = (mMeetingListLiveData.getValue()).size();
        }

        List<MeetingUiModel> meetingUiModelListWithValidDateFilter = new ArrayList<>();
        List<MeetingUiModel> meetingUiModelListWithoutValidDateFilter = new ArrayList<>();

        while (mMeetingListLiveData.getValue() != null && size > index) {

            MeetingUiModel meetingUiModelWithoutValidDateFilter = createMeetingUiModel(index);

            meetingUiModelListWithoutValidDateFilter.add(meetingUiModelWithoutValidDateFilter);

            if (mMeetingListLiveData.getValue().get(index).getDate().toString().equals(dateForFilter)) {

                MeetingUiModel meetingUiModelWithValidDateFilter = createMeetingUiModel(index);

                meetingUiModelListWithValidDateFilter.add(meetingUiModelWithValidDateFilter);
                mMeetingUiModelsLiveData.setValue(meetingUiModelListWithValidDateFilter);

            } else if (dateForFilter.isEmpty()) {
                mMeetingUiModelsLiveData.setValue(meetingUiModelListWithoutValidDateFilter);
            } else if (dateForFilter.length() != 10) {
                mMeetingUiModelsLiveData.setValue(meetingUiModelListWithValidDateFilter);
            } else {
                mMeetingUiModelsLiveData.setValue(meetingUiModelListWithValidDateFilter);
            }
            index++;
        } // END WHILE
    }

    void displayChoiceDateFilterPopup() {
        mChoiceDateFilterUiModel.setTitle("Choisis la date à filtrer");
        mChoiceDateFilterUiModel.setMessage("Exemple : 2019-12-01");
        mChoiceDateFilterUiModel.setPositiveButtonText("Valider");
        mChoiceDateFilterUiModel.setToastForDisplayAllMeeting("Tu as choisi d'afficher toutes les réunions ");
        mChoiceDateFilterUiModel.setToastForInvalideDate("La date est invalide : ");
        mChoiceDateFilterUiModel.setToastForValideDate("Tu as choisi d'afficher les réunions de cette date : ");
        mChoiceDateFilterUiModelData.setValue(mChoiceDateFilterUiModel);
    }

    void compareDateToFilter(String dateToFilter) {
        if (dateToFilter.isEmpty()) {
            setToastTextForChoiceDateFilter(mChoiceDateFilterUiModel.getToastForDisplayAllMeeting());
            setDateFilterType(dateToFilter);
        } else if (dateToFilter.length() != 10) {
            setToastTextForChoiceDateFilter(mChoiceDateFilterUiModel.getToastForInvalideDate());
        } else {
            setToastTextForChoiceDateFilter(mChoiceDateFilterUiModel.getToastForValideDate());
            setDateFilterType(dateToFilter);
        }
    }

    void setToastTextForChoiceDateFilter(String toastText){
        mToastTextForChoiceDateFilter.setValue(toastText);
    }

    // DELETE MEETING
    void deleteMeeting(int meetingId) {
        MeetingManager.getInstance().deleteMeeting(meetingId);
    }
}